// code by jph
package ch.ethz.idsc.tensor.sca.win;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.Times;
import ch.ethz.idsc.tensor.sca.Exp;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** GaussianWindow[1/2]=0.24935220877729616
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/GaussianWindow.html">GaussianWindow</a> */
public enum GaussianWindow implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  private static final Scalar _50_9 = RationalScalar.of(-50, 9);

  @Override
  public Scalar apply(Scalar x) {
    return StaticHelper.SEMI.isInside(x) //
        ? Exp.FUNCTION.apply(Times.of(_50_9, x, x))
        : RealScalar.ZERO;
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their function value */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
