// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.GaussScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class UnitStepTest extends TestCase {
  public void testRealScalar() {
    assertEquals(UnitStep.of(RealScalar.of(-.3)), RealScalar.ZERO);
    assertEquals(UnitStep.of(RealScalar.of(0)), RealScalar.ONE);
    assertEquals(UnitStep.of(RealScalar.of(.134)), RealScalar.ONE);
  }

  public void testPredicateQuantity() {
    assertEquals(UnitStep.of(Quantity.of(-.3, "m")), RealScalar.ZERO);
    assertEquals(UnitStep.of(Quantity.of(0.0, "m")), RealScalar.ONE);
    assertEquals(UnitStep.of(Quantity.of(0, "m")), RealScalar.ONE);
    assertEquals(UnitStep.of(Quantity.of(1, "m")), RealScalar.ONE);
  }

  public void testFail() {
    Scalar scalar = GaussScalar.of(2, 7);
    try {
      UnitStep.FUNCTION.apply(scalar);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
