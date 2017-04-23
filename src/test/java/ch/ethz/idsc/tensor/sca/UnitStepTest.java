// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.ZeroScalar;
import junit.framework.TestCase;

public class UnitStepTest extends TestCase {
  public void testSimple() {
    assertEquals(UnitStep.of(RealScalar.of(-.3)), ZeroScalar.get());
    assertEquals(UnitStep.of(RealScalar.of(0)), RealScalar.ONE);
    assertEquals(UnitStep.of(RealScalar.of(.134)), RealScalar.ONE);
  }
}
