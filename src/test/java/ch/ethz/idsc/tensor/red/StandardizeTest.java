// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.ExactTensorQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class StandardizeTest extends TestCase {
  public void testNumeric() {
    Tensor tensor = Standardize.ofVector(Tensors.vector(6.5, 3.8, 6.6, 5.7, 6.0, 6.4, 5.3));
    assertTrue(Chop._12.allZero(Mean.of(tensor)));
    assertTrue(Chop._12.close(Variance.ofVector(tensor), RealScalar.ONE));
    assertTrue(Chop._12.close(StandardDeviation.ofVector(tensor), RealScalar.ONE));
  }

  public void testExact1() {
    Tensor tensor = Standardize.ofVector(Tensors.vector(1, 2, 3));
    assertTrue(ExactTensorQ.of(tensor));
    assertEquals(tensor, Tensors.vector(-1, 0, 1));
  }

  public void testExact2() {
    Tensor tensor = Standardize.ofVector(Tensors.vector(1, 3, 5));
    assertTrue(ExactTensorQ.of(tensor));
    assertEquals(tensor, Tensors.vector(-1, 0, 1));
  }

  public void testLengthShort() {
    try {
      Standardize.ofVector(Tensors.empty());
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      Standardize.ofVector(Tensors.vector(2));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testScalarFail() {
    try {
      Standardize.ofVector(RealScalar.of(84.312));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testMatrixFail() {
    try {
      Standardize.ofVector(HilbertMatrix.of(5));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
