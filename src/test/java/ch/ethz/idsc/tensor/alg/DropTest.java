// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import junit.framework.TestCase;

public class DropTest extends TestCase {
  public void testHead() {
    assertEquals(Drop.head(Tensors.empty(), 0), Tensors.empty());
    assertEquals(Drop.head(Tensors.vector(9, 8, 3), 1), Tensors.vector(8, 3));
  }

  public void testMatrix() {
    assertEquals(Drop.tail(HilbertMatrix.of(10, 10), 2), HilbertMatrix.of(8, 10));
    assertEquals(Drop.head(HilbertMatrix.of(10, 10), 2), HilbertMatrix.of(10, 10).extract(2, 10));
  }

  public void testHeadFail() {
    Drop.head(Tensors.vector(1, 2), 0);
    Drop.head(Tensors.vector(1, 2), 1);
    Drop.head(Tensors.vector(1, 2), 2);
    try {
      Drop.head(Tensors.vector(1, 2), 3);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Drop.head(Tensors.vector(1, 2), -1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testTailFail() {
    Drop.tail(Tensors.vector(1, 2), 0);
    Drop.tail(Tensors.vector(1, 2), 1);
    Drop.tail(Tensors.vector(1, 2), 2);
    try {
      Drop.tail(Tensors.vector(1, 2), 3);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Drop.tail(Tensors.vector(1, 2), -1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
