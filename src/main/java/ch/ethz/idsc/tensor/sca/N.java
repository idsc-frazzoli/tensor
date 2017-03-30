// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

public enum N implements Function<Scalar, Scalar> {
  function;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof RealScalar)
      return DoubleScalar.of(scalar.number().doubleValue());
    if (scalar instanceof ComplexScalar) {
      ComplexScalar complexScalar = (ComplexScalar) scalar;
      return ComplexScalar.of( //
          apply(complexScalar.real()), //
          apply(complexScalar.imag()));
    }
    return null;
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their decimal numerical */
  public static Tensor of(Tensor tensor) {
    return tensor.map(N.function);
  }
}
