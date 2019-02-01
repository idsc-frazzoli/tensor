// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import junit.framework.TestCase;

public class GaussianEliminationTest extends TestCase {
  public void testPackageVisibility() {
    int modifiers = GaussianElimination.class.getModifiers();
    assertEquals(modifiers & 0x1, 0x0); // non public but package
  }

  public void testPivots() {
    Tensor gf1 = GaussianForm.rowReduce(HilbertMatrix.of(3, 5), PivotArgMaxAbs.INSTANCE);
    Tensor gf2 = GaussianForm.rowReduce(HilbertMatrix.of(3, 5), PivotFirstNonZero.INSTANCE);
    assertEquals(gf1, gf2);
  }
}
