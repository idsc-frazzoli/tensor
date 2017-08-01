// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;

public enum InvertUnlessZero implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  /** @param scalar
   * @return Scalars.isZero(scalar) ? scalar : scalar.invert(); */
  @Override
  public Scalar apply(Scalar scalar) {
    // FIXME possibly check if scalar.invert() is a number, otherwise 0
    return Scalars.isZero(scalar) ? scalar : scalar.invert();
  }

  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
