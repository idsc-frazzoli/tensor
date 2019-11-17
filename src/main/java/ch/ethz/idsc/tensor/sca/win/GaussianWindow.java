// code by jph
package ch.ethz.idsc.tensor.sca.win;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Exp;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** GaussianWindow[1/2]=0.24935220877729616
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/GaussianWindow.html">GaussianWindow</a> */
public class GaussianWindow implements ScalarUnaryOperator {
  private static final Scalar N1_2 = DoubleScalar.of(-0.5);
  /** gaussian window with standard deviation of sigma 3/10 */
  public static final ScalarUnaryOperator FUNCTION = new GaussianWindow(RationalScalar.of(3, 10));
  // ---
  private final Scalar sigma;

  /** @param alpha */
  public GaussianWindow(Scalar sigma) {
    this.sigma = sigma;
  }

  @Override
  public Scalar apply(Scalar x) {
    if (StaticHelper.SEMI.isInside(x)) {
      Scalar ratio = x.divide(sigma);
      return Exp.FUNCTION.apply(ratio.multiply(ratio).multiply(N1_2));
    }
    return RealScalar.ZERO;
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their function value using the
   * standard deviation of 3/10 */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
