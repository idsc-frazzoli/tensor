// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** Examples:
 * <pre>
 * Floor[+3.9] == +3
 * Floor[-8.2] == -9
 * </pre>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Floor.html">Floor</a> */
public enum Floor implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  /** @param scalar instance if {@link RealScalar}
   * @return best integer scalar approximation to floor of scalar
   * @throws TensorRuntimeException if scalar is Infinity, or NaN */
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof RoundingInterface) {
      RoundingInterface roundingInterface = (RoundingInterface) scalar;
      return roundingInterface.floor();
    }
    throw TensorRuntimeException.of(scalar);
  }

  /** @param tensor
   * @return tensor with all entries replaced by their floor */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }

  /** @param increment non-zero
   * @return */
  public static ScalarUnaryOperator toMultipleOf(Scalar increment) {
    return scalar -> FUNCTION.apply(scalar.divide(increment)).multiply(increment);
  }
}
