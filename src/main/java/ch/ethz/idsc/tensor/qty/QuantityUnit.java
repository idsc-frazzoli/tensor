// code by jph
package ch.ethz.idsc.tensor.qty;

import java.util.Objects;

import ch.ethz.idsc.tensor.Scalar;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/QuantityUnit.html">QuantityUnit</a> */
public enum QuantityUnit {
  ;
  /** @param scalar
   * @return unit associated with the specified scalar */
  public static Unit of(Scalar scalar) {
    if (scalar instanceof Quantity) {
      Quantity quantity = (Quantity) scalar;
      return quantity.unit();
    }
    Objects.requireNonNull(scalar);
    return Unit.ONE;
  }
}
