// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class UnitTest extends TestCase {
  public void testString() {
    String check = "m*s^3";
    Unit unit = Unit.of(check);
    assertEquals(unit.toString(), check);
  }

  public void testSpaces() {
    assertEquals(Unit.of(" m ").toString(), "m");
    assertEquals(Unit.of(" m ^ 3 ").toString(), "m^3");
    assertEquals(Unit.of(" m ^ 3 * rad ").toString(), "m^3*rad");
  }

  public void testEqualsHash() {
    Unit kg1 = Unit.of("kg");
    Unit kg2 = Unit.of("kg*m");
    Unit m = Unit.of("m");
    assertEquals(kg1, kg2.add(m.negate()));
    assertEquals(kg1.hashCode(), kg2.add(m.negate()).hashCode());
    assertFalse(kg1.equals(m));
    assertFalse(kg1.equals(new Object()));
  }

  public void testMultiplyZero() {
    Unit unit = Unit.of("kg");
    Unit gone = unit.multiply(RealScalar.ZERO);
    assertTrue(Units.isOne(gone));
  }

  public void testMultiplyZero2() {
    Unit unit = Unit.of("kg*m^-3");
    Unit gone = unit.multiply(RealScalar.ZERO);
    assertTrue(Units.isOne(gone));
  }

  public void testMultiplyFail() {
    Unit kg1 = Unit.of("kg");
    Scalar q = Quantity.of(3, "m");
    try {
      kg1.multiply(q);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFail() {
    Unit.of("*"); // gives unit ONE, not necessarily an error
    try {
      Unit.of(" m >");
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Unit.of("| m ");
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Unit.of("^");
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Unit.of(" ");
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNullFail() {
    try {
      Unit.of(null);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
