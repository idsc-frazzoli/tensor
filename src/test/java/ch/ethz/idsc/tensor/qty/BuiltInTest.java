// code by jph
package ch.ethz.idsc.tensor.qty;

import junit.framework.TestCase;

public class BuiltInTest extends TestCase {
  public void testSize() {
    assertTrue(48 <= UnitSystem.SI().map().size());
  }
}
