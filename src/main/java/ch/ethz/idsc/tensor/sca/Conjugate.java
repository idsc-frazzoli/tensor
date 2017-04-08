// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Conjugate.html">Conjugate</a> */
public enum Conjugate implements Function<Scalar, Scalar> {
  function;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof ConjugateInterface)
      return ((ConjugateInterface) scalar).conjugate();
    throw TensorRuntimeException.of(scalar);
  }

  /** @param tensor
   * @return tensor with all entries conjugated */
  public static Tensor of(Tensor tensor) {
    return tensor.map(Conjugate.function);
  }
  // TODO more tests!
}
