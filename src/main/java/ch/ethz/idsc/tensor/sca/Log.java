// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** logarithm
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Log.html">Log</a> */
public enum Log implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof LogInterface) {
      LogInterface logInterface = (LogInterface) scalar;
      return logInterface.log();
    }
    throw TensorRuntimeException.of(scalar);
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their logarithm */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }

  /** for natural logarithm use {@link Log},
   * for base 10 use {@link Log10}.
   * 
   * @param base
   * @return logarithm function with given base */
  public static ScalarUnaryOperator base(Scalar base) {
    Scalar log_b = FUNCTION.apply(base);
    return scalar -> FUNCTION.apply(scalar).divide(log_b);
  }
}
