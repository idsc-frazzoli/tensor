// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.opt.Projection;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.qty.QuantityTensor;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Conjugate;
import junit.framework.TestCase;

public class NormalizeTest extends TestCase {
  // function requires that vector != 0
  private static void _checkNormalize(Tensor vector, Norm norm) {
    Scalar value = norm.of(Normalize.with(norm::ofVector).apply(vector));
    assertTrue(Chop._13.close(value, RealScalar.ONE));
    assertTrue(Chop._13.close(norm.of(NormalizeUnlessZero.with(norm::ofVector).apply(vector)), RealScalar.ONE));
  }

  private static void _checkNormalizeAllNorms(Tensor vector) {
    _checkNormalize(vector, Norm._1);
    _checkNormalize(vector, Norm.INFINITY);
    _checkNormalize(vector, Norm._2);
  }

  public void testVector1() {
    Tensor vector = Tensors.vector(3, 3, 3, 3);
    Tensor n = Normalize.with(Norm._2::ofVector).apply(vector);
    assertEquals(n.toString(), "{1/2, 1/2, 1/2, 1/2}");
    _checkNormalizeAllNorms(vector);
    _checkNormalizeAllNorms(Tensors.vector(3, 2, 1));
  }

  public void testVector2() {
    Distribution distribution = NormalDistribution.standard();
    _checkNormalizeAllNorms(RandomVariate.of(distribution, 1000));
    _checkNormalizeAllNorms(RandomVariate.of(distribution, 50000));
  }

  public void testNorm1Documentation() {
    Tensor vector = Tensors.vector(2, -3, 1);
    Tensor result = Normalize.with(Norm._1::ofVector).apply(vector);
    assertEquals(result, Tensors.fromString("{1/3, -1/2, 1/6}"));
    assertTrue(ExactScalarQ.all(result));
  }

  public void testNormInfinityDocumentation() {
    Tensor vector = Tensors.vector(2, -3, 1);
    Tensor result = Normalize.with(Norm.INFINITY::ofVector).apply(vector);
    assertEquals(result, Tensors.fromString("{2/3, -1, 1/3}"));
    assertTrue(ExactScalarQ.all(result));
  }

  public void testEps() {
    Tensor vector = Tensors.vector(0, Double.MIN_VALUE, 0);
    assertEquals(Normalize.with(Norm._1::ofVector).apply(vector), Tensors.vector(0, 1, 0));
    assertEquals(Normalize.with(Norm._2::ofVector).apply(vector), Tensors.vector(0, 1, 0));
    assertEquals(Normalize.with(Norm.INFINITY::ofVector).apply(vector), Tensors.vector(0, 1, 0));
  }

  public void testEps2() {
    _checkNormalizeAllNorms(Tensors.vector(0, -Double.MIN_VALUE, 0, 0, 0));
    _checkNormalizeAllNorms(Tensors.vector(0, 0, Double.MIN_VALUE));
    _checkNormalizeAllNorms(Tensors.vector(0, Double.MIN_VALUE, Double.MIN_VALUE));
    _checkNormalizeAllNorms(Tensors.vector(Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE));
  }

  public void testNorm1() {
    Tensor v = Tensors.vector(1, 1, 1);
    Tensor n = Normalize.with(Norm._1::ofVector).apply(v);
    assertEquals(n, Tensors.fromString("{1/3, 1/3, 1/3}"));
    _checkNormalizeAllNorms(v);
  }

  public void testNormInf() {
    Tensor d = Tensors.vector(1, 1, 1).multiply(RealScalar.of(2));
    Tensor n = Normalize.with(Norm.INFINITY::ofVector).apply(d);
    assertEquals(n, Tensors.vector(1, 1, 1));
    assertTrue(ExactScalarQ.all(n));
    _checkNormalizeAllNorms(d);
    _checkNormalizeAllNorms(n);
  }

  public void testComplex() {
    Tensor vector = Tensors.fromString("{1+I,2*I,-3-9.2*I}");
    Tensor s = Normalize.with(Norm._2::ofVector).apply(vector);
    assertTrue(Chop._13.close(s.dot(Conjugate.of(s)), RealScalar.ONE));
    assertTrue(Chop._13.close(Conjugate.of(s).dot(s), RealScalar.ONE));
  }

  public void testComplex2() {
    Tensor vector = Tensors.fromString("{3*I,4}");
    Tensor s = Normalize.with(Norm._2::ofVector).apply(vector);
    assertEquals(Projection.on(vector).apply(s), s);
    assertEquals(Projection.on(s).apply(vector), vector);
    assertTrue(Chop._13.close(s.dot(Conjugate.of(s)), RealScalar.ONE));
    assertTrue(Chop._13.close(Conjugate.of(s).dot(s), RealScalar.ONE));
  }

  public void testQuantityTensor() {
    Tensor vector = QuantityTensor.of(Tensors.vector(2, 3, 4), "m*s^-1");
    _checkNormalizeAllNorms(vector);
  }
}
