// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Transpose;
import junit.framework.TestCase;

public class HilbertMatrixTest extends TestCase {
  public void testMatrix() {
    Tensor m = HilbertMatrix.of(3, 4);
    Tensor d = Transpose.of(m);
    assertEquals(d, HilbertMatrix.of(4, 3));
  }

  public void testInverse() {
    Tensor m = HilbertMatrix.of(4, 4);
    Tensor mi = Inverse.of(m);
    Tensor ci = Tensors.fromString("{{16, -120, 240, -140}, {-120, 1200, -2700, 1680}, {240, -2700, 6480, -4200}, {-140, 1680, -4200, 2800}}");
    assertEquals(mi, ci);
  }

  public void testFail() {
    try {
      HilbertMatrix.of(0, 4);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      HilbertMatrix.of(4, 0);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
