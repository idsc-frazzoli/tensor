// code by jph
package ch.ethz.idsc.tensor.lie;

import java.util.Arrays;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.VectorQ;
import junit.framework.TestCase;

public class TensorContractTest extends TestCase {
  public void testRank3() {
    Tensor vector = TensorContract.of(LieAlgebras.so3(), 0, 2);
    assertTrue(VectorQ.ofLength(vector, 3));
    assertEquals(vector, Array.zeros(3));
    assertTrue(ExactScalarQ.all(vector));
  }

  public void testFail() {
    try {
      TensorContract.of(LieAlgebras.so3(), 0, 3);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testContraction() {
    Tensor array = Array.of(Tensors::vector, 2, 3, 2, 4);
    try {
      TensorContract.of(array, 0, 3);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    Tensor tensor = TensorContract.of(array, 0, 2);
    assertEquals(Dimensions.of(tensor), Arrays.asList(3, 4, 4));
  }
}
