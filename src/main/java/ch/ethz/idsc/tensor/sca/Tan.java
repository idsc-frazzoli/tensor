// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Tan.html">Tan</a> */
public enum Tan implements Function<Scalar, Scalar> {
  function;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof RealScalar) {
      double value = scalar.number().doubleValue();
      return DoubleScalar.of(Math.tan(value));
    }
    if (scalar instanceof ComplexScalar) {
      ComplexScalar z = (ComplexScalar) scalar;
      return Sin.function.apply(z).divide(Cos.function.apply(z));
    }
    throw TensorRuntimeException.of(scalar);
  }

  /** @param tensor
   * @return tensor with all entries replaced by their tan */
  public static Tensor of(Tensor tensor) {
    return tensor.map(Tan.function);
  }
}
