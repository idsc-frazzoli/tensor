// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class RotateLeftTest extends TestCase {
  public void testVector() {
    Tensor vector = Tensors.vector(0, 1, 2, 3, 4);
    assertEquals(RotateLeft.of(vector, -6), Tensors.vector(4, 0, 1, 2, 3));
    assertEquals(RotateLeft.of(vector, -1), Tensors.vector(4, 0, 1, 2, 3));
    assertEquals(RotateLeft.of(vector, +0), Tensors.vector(0, 1, 2, 3, 4));
    assertEquals(RotateLeft.of(vector, +1), Tensors.vector(1, 2, 3, 4, 0));
    assertEquals(RotateLeft.of(vector, +2), Tensors.vector(2, 3, 4, 0, 1));
    assertEquals(RotateLeft.of(vector, +7), Tensors.vector(2, 3, 4, 0, 1));
  }

  public void testFailNull() {
    try {
      RotateLeft.of(null, 0);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
