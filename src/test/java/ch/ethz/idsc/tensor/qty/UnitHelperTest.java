// code by jph
package ch.ethz.idsc.tensor.qty;

import junit.framework.TestCase;

public class UnitHelperTest extends TestCase {
  private static void _confirmFail(String string) {
    try {
      UnitHelper.MEMO.lookup(string);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testLookup() {
    UnitHelper.MEMO.lookup("A*kg^-1*s^2");
    UnitHelper.MEMO.lookup("HaqiuytasdMAM");
    UnitHelper.MEMO.lookup("HaqiuytasdMAM*ASsdlfkjhKJG");
    UnitHelper.MEMO.lookup("HaqiuytasdMAM*ASsdlfkjhKJG^3");
    assertEquals(UnitHelper.MEMO.lookup(""), Unit.ONE);
  }

  public void testFail() {
    _confirmFail("Haqiuyt asdMAM");
    _confirmFail("HaqiuytasdMAM_");
    _confirmFail("HaqiuytasdMAM2");
    _confirmFail("Haqiuyta2sdMAM");
    _confirmFail("Haqiuyta2sdMAM^3");
    _confirmFail("2m");
    _confirmFail("m2");
    _confirmFail("m^2^2");
    _confirmFail("m*2^2");
    _confirmFail("^2");
  }

  public void testDubious() {
    assertEquals(UnitHelper.MEMO.lookup("*a"), UnitHelper.MEMO.lookup("a"));
    assertEquals(UnitHelper.MEMO.lookup("a*"), UnitHelper.MEMO.lookup("a"));
    assertEquals(UnitHelper.MEMO.lookup("a***"), UnitHelper.MEMO.lookup("a"));
    assertEquals(UnitHelper.MEMO.lookup("**a***b**"), UnitHelper.MEMO.lookup("a*b"));
  }
}
