// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.TensorRuntimeException;

// DEPRECATED
public enum Units {
  ;
  /** Example in Mathematica: Quantity[1.2, "Radians"] */
  public static final Unit RADIANS = Unit.of("rad");
  /** Example in Mathematica: Sin[360 Degree] == 0 */
  public static final Unit DEGREE = Unit.of("deg");
  // ---
  private static final Scalar RAD2DEG = RealScalar.of(0.017453292519943295769);

  /** @param quantity
   * @return scalar value of quantity in radians
   * @throws Exception if given quantity has neither unit [rad] or [deg] */
  public static Scalar radiansValue(Quantity quantity) {
    Unit unit = quantity.unit();
    if (unit.equals(Units.RADIANS))
      return quantity.value();
    if (unit.equals(Units.DEGREE))
      return quantity.value().multiply(RAD2DEG);
    throw TensorRuntimeException.of(quantity);
  }
}
