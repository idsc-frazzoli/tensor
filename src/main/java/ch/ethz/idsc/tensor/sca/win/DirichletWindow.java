// code by jph
package ch.ethz.idsc.tensor.sca.win;

import ch.ethz.idsc.tensor.Scalar;
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
}
