// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** Log.of(1.0) returns {@link ZeroScalar}.
 * 
 * inspired by
 * <a href="https://reference.wolfram.com/language/ref/Log.html">Log</a> */
public enum Log implements Function<Scalar, Scalar> {
  function;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof RealScalar) {
      double value = scalar.number().doubleValue();
      if (0 <= value)
        return DoubleScalar.of(Math.log(value));
      return ComplexScalar.of(Math.log(-value), Math.PI);
    }
    if (scalar instanceof ComplexScalar) {
      ComplexScalar z = (ComplexScalar) scalar;
      return ComplexScalar.of(Log.function.apply(z.abs()), Arg.function.apply(z));
    }
    throw TensorRuntimeException.of(scalar);
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their logarithm */
  public static Tensor of(Tensor tensor) {
    return tensor.map(Log.function);
  }
}
