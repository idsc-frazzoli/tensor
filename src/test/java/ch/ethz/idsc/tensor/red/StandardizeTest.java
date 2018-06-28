// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class StandardizeTest extends TestCase {
  public void testNumeric() {
    Tensor res = Standardize.of(Tensors.vector(6.5, 3.8, 6.6, 5.7, 6.0, 6.4, 5.3));
    assertTrue(Chop._12.allZero(Mean.of(res)));
    assertTrue(Chop._12.close(Variance.ofVector(res), RealScalar.ONE));
    assertTrue(Chop._12.close(StandardDeviation.ofVector(res), RealScalar.ONE));
  }

  public void testExact1() {
    Tensor res = Standardize.of(Tensors.vector(1, 2, 3));
    assertTrue(ExactScalarQ.all(res));
    assertEquals(res, Tensors.vector(-1, 0, 1));
  }

  public void testExact2() {
    Tensor res = Standardize.of(Tensors.vector(1, 3, 5));
    assertTrue(ExactScalarQ.all(res));
    assertEquals(res, Tensors.vector(-1, 0, 1));
  }

  public void testLengthShort() {
    try {
      Standardize.of(Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Standardize.of(Tensors.vector(2));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testScalarFail() {
    try {
      Standardize.of(RealScalar.of(84.312));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testMatrixFail() {
    try {
      Standardize.of(HilbertMatrix.of(5));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
