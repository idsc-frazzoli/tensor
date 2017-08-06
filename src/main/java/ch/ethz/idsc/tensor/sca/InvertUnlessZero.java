// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;

/** Three notes:
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
 * may still result in Infinity, for instance.
 * 
 * 3)
 * better numerical accuracy is achieved by using direct division
 * a / b instead of a * (1 / b) */
public enum InvertUnlessZero implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  /** @param scalar
   * @return Scalars.isZero(scalar) ? scalar : scalar.reciprocal(); */
  @Override
  public Scalar apply(Scalar scalar) {
    return Scalars.isZero(scalar) ? scalar : scalar.reciprocal();
  }

  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
