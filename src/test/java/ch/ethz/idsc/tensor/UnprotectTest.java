// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import junit.framework.TestCase;

public class UnprotectTest extends TestCase {
  public void testSimple() {
    assertEquals(Unprotect.length0(Tensors.vector(1, 2, 3)), Scalar.LENGTH);
    assertEquals(Unprotect.length0(HilbertMatrix.of(2, 4)), 4);
  }

  public void testFail() {
    try {
      Unprotect.length0(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
