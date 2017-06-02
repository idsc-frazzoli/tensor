// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/UnitStep.html">UnitStep</a> */
public enum UnitStep implements Function<Scalar, Scalar> {
  function;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    return Scalars.lessThan(scalar, RealScalar.ZERO) ? RealScalar.ZERO : RealScalar.ONE;
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their exponential */
  public static Tensor of(Tensor tensor) {
    return tensor.map(UnitStep.function);
  }
}
