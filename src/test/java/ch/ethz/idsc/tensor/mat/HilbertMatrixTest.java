// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Transpose;
import junit.framework.TestCase;

public class HilbertMatrixTest extends TestCase {
  public void testMatrix() {
    Tensor m = HilbertMatrix.of(3, 4);
    Tensor d = Transpose.of(m);
    assertEquals(d, HilbertMatrix.of(4, 3));
  }
}
