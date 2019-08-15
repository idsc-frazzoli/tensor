// code by jph
package ch.ethz.idsc.tensor.qty;

import java.io.IOException;

import ch.ethz.idsc.tensor.io.Serialization;
import junit.framework.TestCase;

public class KnownUnitQTest extends TestCase {
  public void testKnownUnitQ() {
    assertTrue(KnownUnitQ.SI().of(Unit.of("kgf^2*K*gal^-1")));
  }

  public void testSimple() {
    assertTrue(KnownUnitQ.SI().of(Unit.of("V*K*CD*kOhm^-2")));
    assertTrue(KnownUnitQ.SI().of(Unit.of("PS^3")));
    assertFalse(KnownUnitQ.SI().of(Unit.of("CHF")));
    assertFalse(KnownUnitQ.SI().of(Unit.of("CHF*K")));
    assertFalse(KnownUnitQ.SI().of(Unit.of("CHF*m")));
  }

  public void testFail() throws ClassNotFoundException, IOException {
    KnownUnitQ knownUnitQ = Serialization.copy(KnownUnitQ.SI());
    try {
      knownUnitQ.of(null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNullFail() {
    try {
      KnownUnitQ.in(null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
