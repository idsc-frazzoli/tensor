// code by jph
package ch.ethz.idsc.tensor.qty;

import java.io.IOException;

import ch.ethz.idsc.tensor.io.Serialization;
import junit.framework.TestCase;

public class CompatibleUnitQTest extends TestCase {
  public void testSimple() throws ClassNotFoundException, IOException {
    CompatibleUnitQ compatibleUnitQ = Serialization.copy(CompatibleUnitQ.SI());
    assertTrue(compatibleUnitQ.with(Unit.of("m*s^-1")).test(Quantity.of(2, "km*ms^-1")));
    assertTrue(CompatibleUnitQ.SI().with(Unit.of("PS^2")).test(Quantity.of(2, "W^2")));
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
