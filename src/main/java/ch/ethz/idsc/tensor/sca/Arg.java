// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** gives the argument of a number in the complex plane
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Arg.html">Arg</a> */
public enum Arg implements Function<Scalar, Scalar> {
  function;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof ArgInterface)
      return ((ArgInterface) scalar).arg();
    throw TensorRuntimeException.of(scalar);
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their argument */
  public static Tensor of(Tensor tensor) {
    return tensor.map(Arg.function);
  }
}
