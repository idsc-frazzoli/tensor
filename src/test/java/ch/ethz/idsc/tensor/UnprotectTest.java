// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import junit.framework.TestCase;

public class UnprotectTest extends TestCase {
  public void testSimple() {
    assertTrue(Unprotect.length0(Tensors.vector(1, 2, 3)) == Scalar.LENGTH);
    assertTrue(Unprotect.length0(HilbertMatrix.of(2, 4)) == 4);
    assertTrue(Unprotect.length0(Array.zeros(2, 3, 4)) == 3);
  }

  public void testFail1() {
    Tensor unstruct = Tensors.fromString("{{-1,0,1,2},{3,4,5}}");
    assertEquals(unstruct.length(), 2);
    try {
      Unprotect.length0(unstruct);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFail2() {
    try {
      Unprotect.length0(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
