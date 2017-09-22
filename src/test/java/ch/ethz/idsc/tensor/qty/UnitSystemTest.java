// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
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
}
