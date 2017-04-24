// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.ZeroScalar;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Sign.html">Sign</a> */
public enum Sign implements Function<Scalar, Scalar> {
  function;
  // ---
  private static final Scalar NEGATIVE_ONE = RealScalar.ONE.negate();

  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof RealScalar) {
      int sign = ((RealScalar) scalar).signInt();
      return sign == 0 ? ZeroScalar.get() : (sign == 1 ? RealScalar.ONE : NEGATIVE_ONE);
    }
    throw TensorRuntimeException.of(scalar);
  }

  /** @param tensor with {@link RealScalar} entries
   * @return tensor with all scalars replaced with their sign */
  public static Tensor of(Tensor tensor) {
    return tensor.map(Sign.function);
  }
}
