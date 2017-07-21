// code by jph
package ch.ethz.idsc.tensor.qty;

import junit.framework.TestCase;

public class UnitMapTest extends TestCase {
  public void testSimple() {
    String check = "[m*s^3]";
    UnitMap unitMap = UnitMap.of(check);
    assertEquals(unitMap.toString(), check);
  }

  public void testSpaces() {
    assertEquals(UnitMap.of("[ m ]").toString(), "[m]");
    assertEquals(UnitMap.of("[ m ^ 3 ]").toString(), "[m^3]");
    assertEquals(UnitMap.of("[ m ^ 3 * rad ]").toString(), "[m^3*rad]");
  }

  public void testFail() {
    try {
      UnitMap.of("| m ]");
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
