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
    return Scalars.lessThan(scalar, scalar.zero()) ? RealScalar.ZERO : RealScalar.ONE;
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their unit step evaluation */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(UnitStep.function);
  }
}
