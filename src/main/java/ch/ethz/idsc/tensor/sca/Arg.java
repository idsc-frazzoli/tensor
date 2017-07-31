// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** gives the argument of a number in the complex plane
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Arg.html">Arg</a> */
public enum Arg implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof ArgInterface) {
      ArgInterface argInterface = (ArgInterface) scalar;
      return argInterface.arg();
    }
    throw TensorRuntimeException.of(scalar);
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their argument */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
