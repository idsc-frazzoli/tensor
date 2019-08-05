// code by jph
package ch.ethz.idsc.tensor.qty;

import java.io.IOException;
import java.util.Properties;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.red.Total;
import junit.framework.TestCase;

public class UnitSystemTest extends TestCase {
  public void testExact() {
    Scalar scalar = UnitSystem.SI().apply(Quantity.of(3, "Hz^-2*N*m^-1"));
    assertEquals(scalar, Quantity.of(3, "kg"));
    assertTrue(ExactScalarQ.of(scalar));
  }

  public void testScalar() {
    Scalar scalar = RealScalar.ONE;
    Scalar result = UnitSystem.SI().apply(scalar);
    assertEquals(result, scalar);
    ExactScalarQ.require(result);
  }

  public void testVoltage() {
    Scalar normal = UnitSystem.SI().apply(Quantity.of(1, "V"));
    assertEquals(normal, Quantity.of(1, "A^-1*kg*m^2*s^-3"));
    ExactScalarQ.require(normal);
  }

  public void testMiles() {
    Scalar normal = UnitSystem.SI().apply(Quantity.of(125, "mi"));
    assertEquals(normal, Quantity.of(201168, "m"));
  }

  public void testNullFail() {
    try {
      UnitSystem.SI().apply(null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testMore() {
    Tensor tensor = Tensors.of( //
        Quantity.of(3, "Hz^-2*N*m^-1"), //
        Quantity.of(3.6, "km*h^-1"), //
        Quantity.of(2, "cm^2"));
    Tensor result = tensor.map(UnitSystem.SI());
    assertEquals(result, Tensors.of( //
        Quantity.of(3, "kg"), //
        Quantity.of(1, "m*s^-1"), //
        Scalars.fromString("1/5000[m^2]")));
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
    assertEquals(prices.apply(Quantity.of(3, "Apples")), Quantity.of(6, "CHF"));
    Tensor cart = Tensors.of(Quantity.of(2, "Apples"), Quantity.of(3, "Chocolates"), Quantity.of(3, "Oranges"));
    try {
      Total.of(cart);
      fail();
    } catch (Exception exception) {
      // ---
    }
    Scalar total = Total.of(cart.map(prices)).Get();
    assertEquals(total, Quantity.of(16, "CHF"));
    Scalar euro = new UnitConvert(prices).to(Unit.of("EUR")).apply(total);
    assertEquals(euro, Quantity.of(12.8, "EUR"));
  }

  public void testKnots() throws ClassNotFoundException, IOException {
    UnitSystem unitSystem = Serialization.copy(UnitSystem.SI());
    Scalar r1 = unitSystem.apply(Quantity.of(1, "knots"));
    Unit unit = QuantityUnit.of(r1);
    assertEquals(unit, Unit.of("m*s^-1"));
    assertTrue(ExactScalarQ.of(r1));
    Scalar r2 = UnitConvert.SI().to(Unit.of("km*h^-1")).apply(r1);
    assertTrue(ExactScalarQ.of(r2));
    Scalar r3 = Quantity.of(RationalScalar.of(463, 250), "km*h^-1");
    assertTrue(ExactScalarQ.of(r3));
    assertEquals(r2, r3);
  }

  public void testUnits() {
    UnitSystem unitSystem = UnitSystem.SI();
    assertTrue(67 <= unitSystem.units().size());
    assertTrue(unitSystem.units().contains("K"));
    assertTrue(unitSystem.units().contains("A"));
    assertTrue(unitSystem.units().contains("V"));
    assertTrue(unitSystem.units().contains("psi"));
    assertFalse(unitSystem.units().contains("CHF"));
    assertFalse(unitSystem.units().contains("USD"));
  }
}
