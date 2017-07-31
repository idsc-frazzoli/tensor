// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Sign.html">Sign</a> */
public enum Sign implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  private static final Scalar[] LOOKUP = { RealScalar.ONE.negate(), RealScalar.ZERO, RealScalar.ONE };

  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof SignInterface) {
      SignInterface signInterface = (SignInterface) scalar;
      return LOOKUP[1 + signInterface.signInt()];
    }
    throw TensorRuntimeException.of(scalar);
  }

  /** @param tensor with {@link RealScalar} entries
   * @return tensor with all scalars replaced with their sign */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
