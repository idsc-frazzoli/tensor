// code by jph
package ch.ethz.idsc.tensor.sca.win;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.qty.Boole;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** DirichletWindow[1/2]=1
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/DirichletWindow.html">DirichletWindow</a> */
public enum DirichletWindow implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  @Override
  public Scalar apply(Scalar x) {
    return Boole.of(StaticHelper.SEMI.isInside(x));
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their function value */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
