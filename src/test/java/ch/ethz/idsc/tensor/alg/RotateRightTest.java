// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import junit.framework.TestCase;

public class RotateRightTest extends TestCase {
  public void testVector() {
    Tensor vector = Tensors.vector(0, 1, 2, 3, 4);
    assertEquals(RotateRight.of(vector, +6), Tensors.vector(4, 0, 1, 2, 3));
    assertEquals(RotateRight.of(vector, +1), Tensors.vector(4, 0, 1, 2, 3));
    assertEquals(RotateRight.of(vector, +0), Tensors.vector(0, 1, 2, 3, 4));
    assertEquals(RotateRight.of(vector, -1), Tensors.vector(1, 2, 3, 4, 0));
    assertEquals(RotateRight.of(vector, -2), Tensors.vector(2, 3, 4, 0, 1));
    assertEquals(RotateRight.of(vector, -7), Tensors.vector(2, 3, 4, 0, 1));
  }

  public void testMatrix() {
    int size = 5;
    for (int k = 0; k < size * 2; ++k) {
      Tensor matrix = RotateRight.of(IdentityMatrix.of(size), -k);
      assertEquals(matrix.get(0), UnitVector.of(size, k % size));
    }
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
