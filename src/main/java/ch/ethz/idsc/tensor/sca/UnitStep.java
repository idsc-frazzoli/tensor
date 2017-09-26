// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** implementation not consistent with Mathematica for Quantity as input:
 * Mathematica::UnitStep[Quantity[1, "Meters"]] == UnitStep[Quantity[1, "Meters"]]
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/UnitStep.html">UnitStep</a> */
// EXPERIMENTAL
public enum UnitStep implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    return Sign.isNegative(scalar) ? RealScalar.ZERO : RealScalar.ONE;
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their unit step evaluation */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
