// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ArcCos.html">ArcCos</a> */
public enum ArcCos implements Function<Scalar, Scalar> {
  function;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof RealScalar) {
      double value = scalar.number().doubleValue();
      if (-1 <= value && value <= 1)
        return DoubleScalar.of(Math.acos(value));
      // TODO arcCos outside [-1 1]
      // return ComplexScalar.of(0, something here);
    }
    // TODO arcCos for complex scalars
    throw TensorRuntimeException.of(scalar);
  }

  public static Tensor of(Tensor tensor) {
    return tensor.map(ArcCos.function);
  }
}
