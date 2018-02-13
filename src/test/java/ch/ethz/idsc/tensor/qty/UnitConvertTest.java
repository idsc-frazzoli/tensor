// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import junit.framework.TestCase;

public class UnitConvertTest extends TestCase {
  public void testKm() {
    UnitConvert unitConvert = UnitConvert.SI();
    Scalar q = Quantity.of(2, "km^2");
    Unit unit = Unit.of("cm^2");
    Scalar scalar = unitConvert.to(unit).apply(q);
    assertEquals(scalar, Quantity.of(20000000000L, "cm^2"));
    assertTrue(ExactScalarQ.of(scalar));
  }

  public void testVelocity() {
    UnitConvert unitConvert = UnitConvert.SI();
    Scalar q = Quantity.of(360, "km*h^-1");
    Scalar scalar = unitConvert.to(Unit.of("m*s^-1")).apply(q);
    assertEquals(scalar, Quantity.of(100, "m*s^-1"));
  }

  public void testRadians() {
    UnitConvert unitConvert = UnitConvert.SI();
    Scalar q = Quantity.of(1, "rad");
    Scalar scalar = unitConvert.to(Unit.of("")).apply(q);
    assertEquals(scalar, Quantity.of(1, ""));
    assertTrue(ExactScalarQ.of(scalar));
  }

  public void testResistance() {
    UnitConvert unitConvert = UnitConvert.SI();
    Scalar q = Quantity.of(2, "mV^-1*mA*s^2");
    Scalar scalar = unitConvert.to(Unit.of("Ohm^-1*s^2")).apply(q);
    assertEquals(scalar, Quantity.of(2, "Ohm^-1*s^2"));
  }

  public void testForce() {
    Scalar force = UnitConvert.SI().to(Unit.of("N")).apply(Quantity.of(981, "cm*kg*s^-2"));
    assertEquals(force, Scalars.fromString("981/100[N]"));
    assertTrue(ExactScalarQ.of(force));
  }

  public void testNauticalMiles() {
    Scalar scalar = Quantity.of(1, "nmi");
    Scalar result = UnitConvert.SI().to(Unit.of("km")).apply(scalar);
    assertEquals(result, Scalars.fromString("1.852[km]"));
    assertTrue(ExactScalarQ.of(scalar));
    assertTrue(ExactScalarQ.of(result));
  }

  public void testFail() {
    Scalar mass = Quantity.of(200, "g"); // gram
    Scalar a = Quantity.of(981, "cm*s^-2");
    Scalar force = mass.multiply(a);
    try {
      UnitConvert.SI().to(Unit.of("m")).apply(force);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailNull() {
    try {
      new UnitConvert(null);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
