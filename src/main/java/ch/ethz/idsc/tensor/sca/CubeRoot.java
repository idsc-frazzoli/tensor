// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** gives the real-valued cube root of a given scalar.
 * the input scalar has to be an instance of the {@link SignInterface}.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/CubeRoot.html">CubeRoot</a> */
public enum CubeRoot implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  private static final ScalarUnaryOperator POWER = Power.function(RationalScalar.of(1, 3));

  @Override
  public Scalar apply(Scalar scalar) {
    return Sign.isPositiveOrZero(scalar) //
        ? POWER.apply(scalar)
        : POWER.apply(scalar.negate()).negate();
  }

  /** @param tensor
   * @return tensor with all entries replaced by their cube root */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
