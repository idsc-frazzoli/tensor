// code by jph
package ch.ethz.idsc.tensor.qty;

import junit.framework.TestCase;

public class KnownUnitQTest extends TestCase {
  public void testSimple() {
    assertTrue(KnownUnitQ.SI().of(Unit.of("V*K*CD*kOhm^-2")));
    assertFalse(KnownUnitQ.SI().of(Unit.of("CHF")));
    assertFalse(KnownUnitQ.SI().of(Unit.of("CHF*K")));
    assertFalse(KnownUnitQ.SI().of(Unit.of("CHF*m")));
  }

  public void testFail() {
    KnownUnitQ knownUnitQ = KnownUnitQ.SI();
    try {
      knownUnitQ.of(null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
