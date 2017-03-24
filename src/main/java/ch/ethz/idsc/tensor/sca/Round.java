// code by jph
package ch.ethz.idsc.tensor.sca;

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
    if (scalar instanceof RealScalar)
      // TODO use bigInteger!?
      return RealScalar.of(Math.round(scalar.number().doubleValue()));
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
