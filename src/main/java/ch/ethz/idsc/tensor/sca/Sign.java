// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Sign.html">Sign</a> */
public enum Sign implements ScalarUnaryOperator {
  function;
  // ---
  private static final Scalar NEGATIVE_ONE = RealScalar.ONE.negate();

  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof SignInterface) {
      SignInterface signInterface = (SignInterface) scalar;
      int sign = signInterface.signInt();
      return sign == 0 ? RealScalar.ZERO : (sign == 1 ? RealScalar.ONE : NEGATIVE_ONE);
    }
    throw TensorRuntimeException.of(scalar);
  }

  /** @param tensor with {@link RealScalar} entries
   * @return tensor with all scalars replaced with their sign */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(Sign.function);
  }
}
