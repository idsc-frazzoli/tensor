// code by jph
package ch.ethz.idsc.tensor.qty;

import java.io.Serializable;
import java.util.Objects;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/UnitConvert.html">UnitConvert</a> */
public class UnitConvert implements Serializable {
  private static final UnitConvert SI = of(BuiltIn.SI.unitSystem);

  public static UnitConvert of(UnitSystem unitSystem) {
    return new UnitConvert(Objects.requireNonNull(unitSystem));
  }

  /** @return instance of UnitConvert that uses the built-in SI convention */
  public static UnitConvert SI() {
    return SI;
  }
  // ---

  private final UnitSystem unitSystem;

  /** @param unitSystem non-null
   * @throws Exception if given {@link UnitSystem} is null */
  private UnitConvert(UnitSystem unitSystem) {
    this.unitSystem = unitSystem;
  }

  /** Example:
   * <code>
   * UnitConvert.SI().to(Unit.of("N")).apply(Quantity.of(981, "cm*kg*s^-2"))
   * == Quantity.fromString("981/100[N]")
   * </code>
   * 
   * @param unit
   * @return operator that maps a quantity to the quantity of given unit */
  public ScalarUnaryOperator to(Unit unit) {
    Scalar base = unitSystem.apply(QuantityImpl.of(RealScalar.ONE, unit));
    return scalar -> Quantity.of(unitSystem.apply(scalar).divide(base), unit);
  }

  /** Example:
   * <code>
   * UnitConvert.SI().to("N").apply(Quantity.of(981, "cm*kg*s^-2"))
   * == Quantity.fromString("981/100[N]")
   * </code>
   * 
   * @param string
   * @return operator that maps a quantity to the quantity of given unit */
  public ScalarUnaryOperator to(String string) {
    return to(Unit.of(string));
  }
}
