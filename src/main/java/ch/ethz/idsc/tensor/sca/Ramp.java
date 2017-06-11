// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.Max;

/** Mathematica uses the definition
 * Ramp[x] == x * UnitStep[x]
 * 
 * The tensor library simply uses
 * Ramp[x] == Max[0, x]
 * 
 * inspired by
 * <a href="https://reference.wolfram.com/language/ref/Ramp.html">Ramp</a> */
public enum Ramp implements ScalarUnaryOperator {
  function;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    return Max.of(scalar.zero(), scalar);
  }

  /** @param tensor with {@link RealScalar} entries
   * @return tensor with all scalars replaced with their ramp */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(Ramp.function);
  }
}
