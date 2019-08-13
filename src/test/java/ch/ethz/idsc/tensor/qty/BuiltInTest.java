// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.sca.N;
import junit.framework.TestCase;

public class BuiltInTest extends TestCase {
  public void testSize() {
    assertTrue(72 <= UnitSystem.SI().map().size());
  }

  public void testInstances() {
    assertEquals(UnitSystem.SI(), BuiltIn.SI.unitSystem);
    // assertEquals(UnitConvert.SI(), BuiltIn.SI.unitConvert);
  }

  public void testCompatibleUnitQ() {
    assertTrue(CompatibleUnitQ.SI().with(Unit.of("kgf^2*L^-3")).test(Quantity.of(2, "N^2*m^-9")));
  }

  public void testKnownUnitQ() {
    assertTrue(KnownUnitQ.SI().of(Unit.of("kgf^2*K*gal^-1")));
  }

  public void testMagnitudeKgf() {
    Scalar scalar = QuantityMagnitude.SI().in("N").apply(Quantity.of(1, "kgf"));
    assertEquals(N.DOUBLE.apply(scalar).number().doubleValue(), 9.80665);
  }
}
