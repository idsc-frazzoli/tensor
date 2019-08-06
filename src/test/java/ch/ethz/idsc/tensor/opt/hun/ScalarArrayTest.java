// code by jph
package ch.ethz.idsc.tensor.opt.hun;

import ch.ethz.idsc.tensor.ExactTensorQ;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import junit.framework.TestCase;

public class ScalarArrayTest extends TestCase {
  public void testMatrix() {
    Tensor tensor = Tensors.fromString("{{1, 2}, {3, 4, 5}}");
    Scalar[][] array = ScalarArray.ofMatrix(tensor);
    Tensor matrix = Tensors.matrix(array);
    assertEquals(tensor, matrix);
    ExactTensorQ.require(matrix);
  }

  public void testVectorFail() {
    try {
      ScalarArray.ofVector(HilbertMatrix.of(3));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testMatrixFail() {
    try {
      ScalarArray.ofMatrix(Array.zeros(2, 2, 2));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
