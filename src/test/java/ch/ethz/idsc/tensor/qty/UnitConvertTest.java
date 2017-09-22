// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class UnitConvertTest extends TestCase {
  public void testKm() {
    UnitConvert unitConvert = UnitConvert.SI();
    Scalar q = Quantity.of(2, "km^2");
    Unit unit = Unit.of("cm^2");
    Scalar scalar = unitConvert.to(unit).apply(q);
    assertEquals(scalar, Quantity.fromString("20000000000[cm^2]"));
  }

  public void testVelocity() {
    UnitConvert quantityConverter = UnitConvert.SI();
    Scalar q = Quantity.of(360, "km*h^-1");
    Scalar scalar = quantityConverter.to(Unit.of("m*s^-1")).apply(q);
    assertEquals(scalar, Quantity.fromString("100[m*s^-1]"));
  }

  public void testRadians() {
    UnitConvert quantityConverter = UnitConvert.SI();
    Scalar q = Quantity.fromString("1[rad]");
    Scalar scalar = quantityConverter.to(Unit.of("")).apply(q);
    assertEquals(scalar, Quantity.fromString("1"));
  }

  public void testForce() {
    Scalar force = UnitConvert.SI().to(Unit.of("N")).apply(Quantity.of(981, "cm*kg*s^-2"));
    assertEquals(force, Quantity.fromString("981/100[N]"));
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
}
