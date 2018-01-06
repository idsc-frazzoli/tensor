// code by jph
package ch.ethz.idsc.tensor.mat;

import junit.framework.TestCase;

public class GaussianEliminationTest extends TestCase {
  public void testPackageVisibility() {
    int modifiers = GaussianElimination.class.getModifiers();
    assertEquals(modifiers & 0x1, 0x0); // non public but package
  }
}
