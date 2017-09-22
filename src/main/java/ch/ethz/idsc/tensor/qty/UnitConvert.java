// code by jph
package ch.ethz.idsc.tensor.qty;

import java.util.Objects;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/UnitConvert.html">UnitConvert</a> */
public class UnitConvert {
  private static final UnitConvert SI = new UnitConvert(UnitSystem.SI());

  public static final UnitConvert SI() {
    return SI;
  }

  private final UnitSystem unitSystem;

  public UnitConvert(UnitSystem unitSystem) {
    if (Objects.isNull(unitSystem))
      throw new NullPointerException();
    this.unitSystem = unitSystem;
  }

  public ScalarUnaryOperator to(Unit unit) {
    final Scalar base = unitSystem.apply(Quantity.of(1, unit.toString()));
    return scalar -> Quantity.of(unitSystem.apply(scalar).divide(base), unit.toString());
  }
}
