// code by jph
package ch.ethz.idsc.tensor.qty;

import java.util.Properties;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.red.Total;
import junit.framework.TestCase;

public class UnitSystemTest extends TestCase {
  public void testSimple() {
    UnitSystem unitSystem = UnitSystem.SI();
    Scalar scalar = unitSystem.apply(Quantity.of(3, "Hz^-2*N*m^-1"));
    assertEquals(scalar, Quantity.fromString("3[kg]"));
  }

  public void testScalar() {
    UnitSystem unitSystem = UnitSystem.SI();
    Scalar scalar = RealScalar.ONE;
    assertEquals(unitSystem.apply(scalar), scalar);
  }

  public void testVoltage() {
    Scalar normal = UnitSystem.SI().apply(Quantity.of(1, "V"));
    assertEquals(normal, Quantity.fromString("1[A^-1*kg*m^2*s^-3]"));
  }

  public void testMiles() {
    Scalar normal = UnitSystem.SI().apply(Quantity.of(125, "mi"));
    assertEquals(normal, Quantity.fromString("201168[m]"));
  }

  public void testNull() {
    UnitSystem unitSystem = UnitSystem.SI();
    unitSystem.apply(null);
  }

  public void testMore() {
    Tensor tensor = Tensors.of( //
        Quantity.of(3, "Hz^-2*N*m^-1"), //
        Quantity.of(3.6, "km*h^-1"), //
        Quantity.of(2, "cm^2"));
    Tensor result = tensor.map(UnitSystem.SI());
    assertEquals(result, Tensors.of( //
        Quantity.fromString("3[kg]"), //
        Quantity.fromString("1[m*s^-1]"), //
        Quantity.fromString("1/5000[m^2]")));
  }

  public void testElectric() {
    UnitSystem unitSystem = UnitSystem.SI();
    Scalar r1 = unitSystem.apply(Quantity.of(3, "Ohm"));
    Scalar r2 = unitSystem.apply(Quantity.of(3, "V*A^-1"));
    assertEquals(r1, r2);
  }

  public void testCustom() {
    Properties properties = new Properties();
    properties.setProperty("EUR", "1.25[CHF]");
    properties.setProperty("Apples", "2[CHF]");
    properties.setProperty("Chocolates", "3[CHF]");
    properties.setProperty("Oranges", "1[CHF]");
    UnitSystem prices = SimpleUnitSystem.from(properties);
    assertEquals(prices.apply(Quantity.of(3, "Apples")), Quantity.fromString("6[CHF]"));
    Tensor cart = Tensors.of(Quantity.of(2, "Apples"), Quantity.of(3, "Chocolates"), Quantity.of(3, "Oranges"));
    try {
      Total.of(cart);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    Scalar total = Total.of(cart.map(prices)).Get();
    assertEquals(total, Quantity.fromString("16[CHF]"));
    Scalar euro = new UnitConvert(prices).to(Unit.of("EUR")).apply(total);
    assertEquals(euro, Quantity.of(12.8, "EUR"));
  }
}
