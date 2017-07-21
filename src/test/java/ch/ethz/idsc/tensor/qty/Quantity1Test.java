// code by jph
package ch.ethz.idsc.tensor.qty;

import junit.framework.TestCase;

public class Quantity1Test extends TestCase {
  public void testSimple() {
    assertTrue(Quantity.fromString("-7[m*kg^-2]") instanceof Quantity);
    assertTrue(Quantity.fromString("3 [ m ]") instanceof Quantity);
    assertTrue(Quantity.fromString("3 [ m *rad ]") instanceof Quantity);
    assertFalse(Quantity.fromString(" 3  ") instanceof Quantity);
  }
}
