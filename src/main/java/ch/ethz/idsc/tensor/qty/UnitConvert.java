// code by jph
package ch.ethz.idsc.tensor.qty;

import java.util.Objects;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/UnitConvert.html">UnitConvert</a> */
public class UnitConvert {
  private static final UnitConvert SI = new UnitConvert(UnitSystem.SI());

  /** @return instance of UnitConvert that uses the built-in SI convention */
  public static final UnitConvert SI() {
    return SI;
  }

  private final UnitSystem unitSystem;

  public UnitConvert(UnitSystem unitSystem) {
    if (Objects.isNull(unitSystem))
      throw new NullPointerException();
    this.unitSystem = unitSystem;
  }

  /** Example:
   * UnitConvert.SI().to(Unit.of("N")).apply(Quantity.of(981, "cm*kg*s^-2"))
   * gives
   * Quantity.fromString("981/100[N]")
   * 
   * @param unit
   * @return operator that maps a quantity to the quantity of given unit */
  public ScalarUnaryOperator to(Unit unit) {
    final Scalar base = unitSystem.apply(Quantity.of(RealScalar.ONE, unit));
    return scalar -> Quantity.of(unitSystem.apply(scalar).divide(base), unit);
  }
}
