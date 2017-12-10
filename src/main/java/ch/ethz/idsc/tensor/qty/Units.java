// code by jph
package ch.ethz.idsc.tensor.qty;

import java.util.Objects;

import ch.ethz.idsc.tensor.Scalar;

/** auxiliary functions and operators for {@link Unit} */
public enum Units {
  ;
  /** @param scalar
   * @return unit of scalar */
  public static Unit of(Scalar scalar) {
    if (scalar instanceof Quantity) {
      Quantity quantity = (Quantity) scalar;
      return quantity.unit();
    }
    if (Objects.isNull(scalar))
      throw new IllegalArgumentException(); // scalar == null
    return Unit.ONE;
  }

  /** @param unit
   * @return true if given unit is dimension-less */
  public static boolean isOne(Unit unit) {
    return Unit.ONE.equals(unit);
  }
}
