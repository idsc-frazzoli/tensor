// code by jph
package ch.ethz.idsc.tensor.sca.win;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** BlackmanNuttallWindow[1/2]=3.628000000000381E-4
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/BlackmanNuttallWindow.html">BlackmanNuttallWindow</a> */
public enum BlackmanNuttallWindow implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  private static final Scalar A0 = RationalScalar.of(3635819, 10000000);
  private static final Scalar A1 = RationalScalar.of(4891775, 10000000);
  private static final Scalar A2 = RationalScalar.of(1365995, 10000000);
  private static final Scalar A3 = RationalScalar.of(106411, 10000000);

  @Override
  public Scalar apply(Scalar x) {
    return StaticHelper.SEMI.isInside(x) //
        ? StaticHelper.deg3(A0, A1, A2, A3, x)
        : RealScalar.ZERO;
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their function value */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
