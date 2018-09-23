// code by jph
package ch.ethz.idsc.tensor;

import java.util.Arrays;

import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.sca.Increment;
import junit.framework.TestCase;

public class ViewTensorTest extends TestCase {
  public void testBlock() {
    Tensor array = Array.zeros(5, 5);
    Tensor refs = Unprotect.references(array);
    refs.block(Arrays.asList(1, 2), Arrays.asList(2, 3)).set(Increment.ONE, Tensor.ALL, Tensor.ALL);
    assertEquals(array, //
        Tensors.fromString("{{0, 0, 0, 0, 0}, {0, 0, 1, 1, 1}, {0, 0, 1, 1, 1}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}}"));
  }

  public void testExtract() {
    Tensor vector = Tensors.vector(1, 2, 3);
    Tensor access = Unprotect.references(vector);
    Tensor slevel = access.extract(1, 3);
    slevel.set(Increment.ONE, 0);
    assertEquals(vector, Tensors.vector(1, 3, 3));
  }

  public void testUnmodifiableFail() {
    try {
      Unprotect.references(Tensors.vector(1, 2, 3).unmodifiable());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testUnmodifiableIterateFail() {
    try {
      Unprotect.references(Tensors.matrixInt(new int[][] { { 1, 2, 3 } }).unmodifiable().iterator().next());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testUnmodifiableLoopFail() {
    for (Tensor tensor : Tensors.matrixInt(new int[][] { { 1, 2 }, { 3, 4, 5 } }).unmodifiable())
      try {
        Unprotect.references(tensor);
        assertTrue(false);
      } catch (Exception exception) {
        // ---
      }
  }
}
