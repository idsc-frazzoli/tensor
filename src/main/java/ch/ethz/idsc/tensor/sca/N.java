// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** maps scalars that implement {@link NInterface} to their numerical approximation.
 * 
 * <p>For instance, N converts a {@link RationalScalar} to a {@link DoubleScalar}.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/N.html">N</a> */
public enum N implements ScalarUnaryOperator {
  function;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof NInterface)
      return ((NInterface) scalar).n();
    return scalar;
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their decimal numerical */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(N.function);
  }
}
