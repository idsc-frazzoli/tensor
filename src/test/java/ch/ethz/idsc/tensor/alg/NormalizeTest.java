// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.opt.Projection;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.qty.QuantityTensor;
import ch.ethz.idsc.tensor.red.Frobenius;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Conjugate;
import junit.framework.TestCase;

public class NormalizeTest extends TestCase {
  // function requires that vector != 0
  private static void _checkNormalize(Tensor vector, Norm norm) {
    Scalar n = norm.of(Normalize.of(vector, norm));
    // System.out.println(n);
    assertTrue(Chop._13.close(n, RealScalar.ONE));
    assertTrue(Chop._13.close(norm.of(Normalize.unlessZero(vector, norm)), RealScalar.ONE));
  }

  private static void _checkNormalizeAllNorms(Tensor vector) {
    _checkNormalize(vector, Norm._1);
    _checkNormalize(vector, Norm.INFINITY);
    _checkNormalize(vector, Norm._2);
  }

  public void testVector1() {
    Tensor vector = Tensors.vector(3, 3, 3, 3);
    Tensor n = Normalize.of(vector);
    assertEquals(n.toString(), "{1/2, 1/2, 1/2, 1/2}");
    _checkNormalizeAllNorms(vector);
    _checkNormalizeAllNorms(Tensors.vector(3, 2, 1));
  }

  public void testVector2() {
    Distribution d = NormalDistribution.standard();
    _checkNormalizeAllNorms(RandomVariate.of(d, 1000));
    _checkNormalizeAllNorms(RandomVariate.of(d, 50000));
  }

  public void testEmpty() {
    try {
      Normalize.of(Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testZeros() {
    try {
      Normalize.of(Array.zeros(10));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testEps() {
    Tensor vector = Tensors.vector(0, Double.MIN_VALUE, 0);
    assertEquals(Normalize.of(vector, Norm._1), Tensors.vector(0, 1, 0));
    assertEquals(Normalize.of(vector, Norm._2), Tensors.vector(0, 1, 0));
    assertEquals(Normalize.of(vector, Norm.INFINITY), Tensors.vector(0, 1, 0));
  }

  public void testEps2() {
    _checkNormalizeAllNorms(Tensors.vector(0, -Double.MIN_VALUE, 0, 0, 0));
    _checkNormalizeAllNorms(Tensors.vector(0, 0, Double.MIN_VALUE));
    _checkNormalizeAllNorms(Tensors.vector(0, Double.MIN_VALUE, Double.MIN_VALUE));
    _checkNormalizeAllNorms(Tensors.vector(Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE));
  }

  public void testNorm1() {
    Tensor v = Tensors.vector(1, 1, 1);
    Tensor n = Normalize.of(v, Norm._1);
    assertEquals(n, Tensors.fromString("{1/3, 1/3, 1/3}"));
    _checkNormalizeAllNorms(v);
  }

  public void testNormInf() {
    Tensor d = Tensors.vector(1, 1, 1).multiply(RealScalar.of(2));
    Tensor n = Normalize.of(d, Norm.INFINITY);
    assertEquals(n, Tensors.vector(1, 1, 1));
    _checkNormalizeAllNorms(d);
    _checkNormalizeAllNorms(n);
  }

  public void testOk1() {
    Tensor v = Tensors.vector(0, 0, 0, 0);
    assertEquals(v, Normalize.unlessZero(v));
    for (Norm n : Norm.values())
      assertEquals(v, Normalize.unlessZero(v, n));
  }

  public void testComplex() {
    Tensor vector = Tensors.fromString("{1+I,2*I,-3-9.2*I}");
    Tensor s = Normalize.of(vector);
    assertTrue(Chop._13.close(s.dot(Conjugate.of(s)), RealScalar.ONE));
    assertTrue(Chop._13.close(Conjugate.of(s).dot(s), RealScalar.ONE));
  }

  public void testComplex2() {
    Tensor vector = Tensors.fromString("{3*I,4}");
    Tensor s = Normalize.of(vector);
    assertEquals(Projection.on(vector).apply(s), s);
    assertEquals(Projection.on(s).apply(vector), vector);
    assertTrue(Chop._13.close(s.dot(Conjugate.of(s)), RealScalar.ONE));
    assertTrue(Chop._13.close(Conjugate.of(s).dot(s), RealScalar.ONE));
  }

  public void testQuantityTensor() {
    Tensor vector = QuantityTensor.of(Tensors.vector(2, 3, 4), "m*s^-1");
    _checkNormalizeAllNorms(vector);
  }

  public void testFail1() {
    try {
      Normalize.of(Tensors.vector(0, 0, 0, 0), Norm._1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testScalarFail() {
    try {
      Normalize.of(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testMatrixFail() {
    try {
      Normalize.of(Tensors.fromString("{{1,2},{3,4,5}}"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Normalize.of(HilbertMatrix.of(3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testMatrixFail2() {
    try {
      Normalize.unlessZero(Array.zeros(3, 3), Frobenius.NORM);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
