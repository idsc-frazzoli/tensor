// code by jph
package ch.ethz.idsc.tensor.sca;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Round.html">Round</a> */
public enum Round implements Function<Scalar, Scalar> {
  function;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof RealScalar) {
      BigDecimal bigDecimal = BigDecimal.valueOf(scalar.number().doubleValue());
      return RealScalar.of(bigDecimal.setScale(0, RoundingMode.HALF_UP).toBigIntegerExact());
    }
    throw TensorRuntimeException.of(scalar);
  }

  /** rounds all entries of tensor to nearest integers, with
   * ties rounding to positive infinity.
   * 
   * @param tensor
   * @return Rationalize.of(tensor, 1) */
  public static Tensor of(Tensor tensor) {
    return tensor.map(Round.function);
  }
}
