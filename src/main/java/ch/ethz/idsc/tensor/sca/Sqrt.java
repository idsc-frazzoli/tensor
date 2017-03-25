// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.ZeroScalar;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Sqrt.html">Sqrt</a> */
public enum Sqrt implements Function<Scalar, Scalar> {
  function;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof RealScalar) {
      double value = scalar.number().doubleValue();
      if (0 <= value)
        return DoubleScalar.of(Math.sqrt(value));
      return ComplexScalar.of(ZeroScalar.get(), DoubleScalar.of(Math.sqrt(-value)));
    }
    if (scalar instanceof ComplexScalar)
      return ComplexScalar.fromPolar( //
          Sqrt.function.apply(scalar.abs()), //
          Arg.function.apply(scalar).divide(RealScalar.of(2)));
    throw TensorRuntimeException.of(scalar);
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their square root */
  public static Tensor of(Tensor tensor) {
    return tensor.map(Sqrt.function);
  }
}
