// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** x * x.conjugate() */
public enum AbsSquared implements Function<Scalar, Scalar> {
  function;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof ConjugateInterface)
      return scalar.multiply(Conjugate.function.apply(scalar));
    Scalar abs = scalar.abs();
    return abs.multiply(abs);
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their absolute value */
  public static Tensor of(Tensor tensor) {
    return tensor.map(AbsSquared.function);
  }
}
