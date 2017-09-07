// code by jph
package ch.ethz.idsc.tensor.qty;

import junit.framework.TestCase;

public class UnitTest extends TestCase {
  public void testSimple() {
    String check = "[m*s^3]";
    Unit unit = Unit.of(check);
    assertEquals(unit.toString(), check);
  }

  public void testSpaces() {
    assertEquals(Unit.of("[ m ]").toString(), "[m]");
    assertEquals(Unit.of("[ m ^ 3 ]").toString(), "[m^3]");
    assertEquals(Unit.of("[ m ^ 3 * rad ]").toString(), "[m^3*rad]");
  }

  public void testFail() {
    try {
      Unit.of("[ m >");
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Unit.of("| m ]");
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Unit.of("*");
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

  public void testEqualsHash() {
    Unit kg1 = Unit.of("[kg]");
    Unit kg2 = Unit.of("[kg]");
    Unit m = Unit.of("[m]");
    assertEquals(kg1, kg2);
    assertEquals(kg1.hashCode(), kg2.hashCode());
    assertFalse(kg1.equals(m));
    assertFalse(kg1.equals(new Object()));
  }
}
