// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.red.Frobenius;
import ch.ethz.idsc.tensor.red.Norm;
import junit.framework.TestCase;

public class NormalizeFailTest extends TestCase {
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

  public void testFail1() {
    try {
      Normalize.of(Tensors.vector(0, 0, 0, 0), Norm._1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNormalizePositiveInfinity() {
    Tensor vector = Tensors.of(DoubleScalar.POSITIVE_INFINITY, RealScalar.ONE);
    try {
      Normalize.of(vector);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Normalize.unlessZero(vector);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNormalizeNegativeInfinity() {
    Tensor vector = Tensors.of(DoubleScalar.NEGATIVE_INFINITY, RealScalar.ONE, DoubleScalar.POSITIVE_INFINITY);
    try {
      Normalize.of(vector);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNormalizeNaN() {
    Tensor vector = Tensors.of(RealScalar.ONE, DoubleScalar.INDETERMINATE, RealScalar.ONE);
    try {
      Normalize.of(vector);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Normalize.unlessZero(vector);
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
