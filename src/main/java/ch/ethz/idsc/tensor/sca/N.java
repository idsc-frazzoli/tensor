// code by jph
package ch.ethz.idsc.tensor.sca;

import java.math.MathContext;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** provides the decimal representation of scalars that implement {@link NInterface}.
 * Supported types include {@link RationalScalar}.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/N.html">N</a> */
public enum N implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof NInterface) {
      NInterface nInterface = (NInterface) scalar;
      return nInterface.n();
    }
    return scalar;
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their decimal numerical */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }

  /** @param scalar
   * @param mathContext
   * @return */
  public static Scalar apply(Scalar scalar, MathContext mathContext) {
    if (scalar instanceof NInterface) {
      NInterface nInterface = (NInterface) scalar;
      return nInterface.n(mathContext);
    }
    return scalar;
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their decimal numerical */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor, MathContext mathContext) {
    return (T) tensor.map(scalar -> apply(scalar, mathContext));
  }
}
