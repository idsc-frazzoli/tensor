// code by jph
package ch.ethz.idsc.tensor;

import java.util.Arrays;

import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.sca.Increment;
import junit.framework.TestCase;

public class ViewTensorTest extends TestCase {
  public void testSimple() {
    Tensor array = Array.zeros(5, 5);
    Tensor refs = Unprotect.references(array);
    refs.block(Arrays.asList(1, 2), Arrays.asList(2, 3)).set(Increment.ONE, Tensor.ALL, Tensor.ALL);
    assertEquals(array, //
        Tensors.fromString("{{0, 0, 0, 0, 0}, {0, 0, 1, 1, 1}, {0, 0, 1, 1, 1}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}}"));
  }

  public void testUnmodifiableFail() {
    try {
      Unprotect.references(Tensors.vector(1, 2, 3).unmodifiable());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Unprotect.references(Tensors.matrixInt(new int[][] { { 1, 2, 3 } }).unmodifiable().iterator().next());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
