// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.qty.Quantity;

/** Examples:
 * <pre>
 * UnitStep[-3] == 0
 * UnitStep[Quantity.of(-2.7, "s^-1")] == 0
 * UnitStep[0] == 1
 * UnitStep[10] == 1
 * </pre>
 * 
 * <p>implementation is <em>not</em> consistent with Mathematica for input of type
 * {@link Quantity}:
 * Mathematica::UnitStep[Quantity[1, "Meters"]] == UnitStep[Quantity[1, "Meters"]]
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/UnitStep.html">UnitStep</a> */
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
