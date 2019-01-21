// code by jph
package ch.ethz.idsc.tensor.qty;

import junit.framework.TestCase;

public class CompatibleUnitQTest extends TestCase {
  public void testSimple() {
    assertTrue(CompatibleUnitQ.SI().with(Unit.of("m*s^-1")).test(Quantity.of(2, "km*ms^-1")));
    assertFalse(CompatibleUnitQ.SI().with(Unit.of("m*s^-1")).test(Quantity.of(2, "m*s")));
  }

  public void testWithFail() {
    try {
      CompatibleUnitQ.SI().with(null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testInNullFail() {
    try {
      CompatibleUnitQ.in(null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
