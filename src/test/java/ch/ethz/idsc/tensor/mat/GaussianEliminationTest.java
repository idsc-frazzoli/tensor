// code by jph
package ch.ethz.idsc.tensor.mat;

import junit.framework.TestCase;

public class GaussianEliminationTest extends TestCase {
  public void testPackageVisibility() {
    int modifiers = GaussianElimination.class.getModifiers();
    assertEquals(modifiers & 0x1, 0x0); // non public but package
  }

  public void testPivots() {
    GaussianElimination p1 = new GaussianElimination(HilbertMatrix.of(3, 5), PivotArgMaxAbs.INSTANCE);
    GaussianElimination p2 = new GaussianElimination(HilbertMatrix.of(3, 5), PivotFirstNonZero.INSTANCE);
    assertEquals(p1.det(), p2.det());
    assertEquals(p1.lhs(), p2.lhs());
  }
}
