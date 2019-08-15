// code by jph
package ch.ethz.idsc.tensor.qty;

import junit.framework.TestCase;

public class LazyHolderTest extends TestCase {
  public void testInstances() {
    assertEquals(UnitSystem.SI(), LazyHolder.SI.unitSystem);
  }
}
