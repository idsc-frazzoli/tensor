// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class Normalize2DTest extends TestCase {
  private static Tensor unlessZero(Scalar x, Scalar y) {
    return Normalize.unlessZero(Tensors.of(x, y), Norm._2);
  }

  public void testUp() {
    double eps = Math.nextUp(0.0);
    assertEquals(unlessZero(RealScalar.of(eps), RealScalar.ZERO), Normalize.of(Tensors.vector(1, 0)));
    assertEquals(unlessZero(RealScalar.ZERO, RealScalar.of(eps)), Normalize.of(Tensors.vector(0, 1)));
    assertEquals(unlessZero(RealScalar.of(eps), RealScalar.of(eps)), Normalize.of(Tensors.vector(1, 1)));
  }

  public void testDown() {
    double eps = Math.nextDown(0.0);
    Tensor vec = unlessZero(RealScalar.of(eps), RealScalar.ZERO);
    assertEquals(vec, Tensors.vector(-1, 0));
    assertEquals(unlessZero(RealScalar.ZERO, RealScalar.of(-eps)), Tensors.vector(0, 1));
  }

  public void testZero() {
    Tensor res = unlessZero(RealScalar.ZERO, RealScalar.ZERO);
    assertEquals(res, Array.zeros(2));
  }

  public void testUp2() {
    double eps = Math.nextUp(0.0);
    Tensor vec = unlessZero(RealScalar.of(eps), RealScalar.of(eps));
    assertTrue(Chop._12.close(Norm._2.ofVector(vec), RealScalar.ONE));
  }

  public void testFail() {
    Normalize.unlessZero(Tensors.vectorDouble(0.0, 0.0), Norm._2);
    try {
      Normalize.of(Tensors.vectorDouble(0.0, 0.0));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNumberQFail1() {
    try {
      unlessZero(DoubleScalar.POSITIVE_INFINITY, RealScalar.ZERO);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      unlessZero(DoubleScalar.INDETERMINATE, RealScalar.ZERO);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNumberQFail2() {
    try {
      unlessZero(RealScalar.ZERO, DoubleScalar.POSITIVE_INFINITY);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      unlessZero(RealScalar.ZERO, DoubleScalar.INDETERMINATE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
