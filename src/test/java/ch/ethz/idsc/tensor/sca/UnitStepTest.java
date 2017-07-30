// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import junit.framework.TestCase;

public class UnitStepTest extends TestCase {
  public void testSimple() {
    assertEquals(UnitStep.of(RealScalar.of(-.3)), RealScalar.ZERO);
    assertEquals(UnitStep.of(RealScalar.of(0)), RealScalar.ONE);
    assertEquals(UnitStep.of(RealScalar.of(.134)), RealScalar.ONE);
  }

  public void testPredicate() {
    assertTrue(UnitStep.isNonNegative(RealScalar.ZERO));
    assertTrue(UnitStep.isNonNegative(RealScalar.ONE));
    assertFalse(UnitStep.isNonNegative(RealScalar.ONE.negate()));
  }
}
