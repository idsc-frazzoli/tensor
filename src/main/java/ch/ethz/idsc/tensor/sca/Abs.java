// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

public enum Abs implements Function<Scalar, Scalar> {
  function;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    return scalar.abs();
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their absolute value */
  public static Tensor of(Tensor tensor) {
    return tensor.map(Scalar::abs);
  }
}
