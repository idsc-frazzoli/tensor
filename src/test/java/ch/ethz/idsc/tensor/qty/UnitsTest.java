// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.RealScalar;
import junit.framework.TestCase;

public class UnitsTest extends TestCase {
  public void testZero() {
    Unit unit = Unit.of("m^0*s^-0");
    assertTrue(Units.isOne(unit));
  }

  public void testDouble() {
    assertEquals(Unit.of("m*m^3"), Unit.of("m*m^2*m"));
    assertTrue(Units.isOne(Unit.of("m*m^-1")));
    assertTrue(Units.isOne(Unit.of("s^2*m*s^-1*m^-1*s^-1")));
  }

  public void testEmpty() {
    assertTrue(Units.isOne(Unit.of("")));
    assertTrue(Units.isOne(Unit.ONE));
  }

  public void testScalar() {
    assertEquals(Units.of(RealScalar.ONE), Unit.ONE);
  }

  public void testNullFail() {
    try {
      Units.of(null);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
