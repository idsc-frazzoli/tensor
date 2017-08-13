// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import junit.framework.TestCase;

public class ParallelizeTest extends TestCase {
  public void testDotSimple() {
    Tensor a = Tensors.vector(1, 2, 3);
    Tensor b = Tensors.vector(-3, 4, -8);
    assertEquals(a.dot(b), Parallelize.dot(a, b));
  }

  public void testDotEmpty() {
    Tensor a = Tensors.empty();
    Tensor b = Tensors.empty();
    assertEquals(a.dot(b), Parallelize.dot(a, b));
  }

  public void testDotFail() {
    try {
      Parallelize.dot(RealScalar.ONE, RealScalar.ZERO);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Parallelize.dot(Tensors.vector(1, 2, 3), HilbertMatrix.of(4));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
