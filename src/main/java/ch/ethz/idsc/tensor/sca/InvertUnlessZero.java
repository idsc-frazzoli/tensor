// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;

public enum InvertUnlessZero implements Function<Scalar, Scalar> {
  function;
  // ---
  /** @param scalar
   * @return Scalars.isZero(scalar) ? scalar : scalar.invert(); */
  @Override
  public Scalar apply(Scalar scalar) {
    return Scalars.isZero(scalar) ? scalar : scalar.invert();
  }

  public static Tensor of(Tensor tensor) {
    return tensor.map(InvertUnlessZero.function);
  }
}
