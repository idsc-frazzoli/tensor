// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.qty.Quantity;

/** Hints:
 * 
 * 1)
 * Scalars.isZero(scalar) == false
 * does not imply that scalar.reciprocal() results in a meaningful number
 * 
 * For instance,
 * Scalars.isZero(4.9E-324) == false
 * whereas 1.0 / 4.9E-324 == Infinity
 * 
 * 2)
 * even if scalar.reciprocal() is a number, the product
 * factor.multiply(scalar.reciprocal())
 * may still result in Infinity.
 * 
 * 3)
 * better numerical accuracy is achieved by using direct division
 * a / b instead of a * (1 / b)
 * 
 * 4)
 * for input of type {@link Quantity} the unit is inverted regardless of the value */
public enum InvertUnlessZero implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  /** @param scalar
   * @return if the given scalar is an instance of {@link RealScalar} the function simplifies to
   * Scalars.isZero(scalar) ? scalar : scalar.reciprocal(); */
  @Override
  public Scalar apply(Scalar scalar) {
    if (Scalars.isZero(scalar)) {
      if (scalar instanceof Quantity) {
        Quantity quantity = (Quantity) scalar;
        return Quantity.of(quantity.value(), quantity.unit().negate());
      }
      return scalar;
    }
    return scalar.reciprocal();
  }

  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
