// code by jph
package ch.ethz.idsc.tensor.num;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.sca.Mod;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/GCD.html">GCD</a> */
public enum GCD {
  ;
  /** @param a scalar in exact precision
   * @param b scalar in exact precision
   * @return greatest common divider of a and b
   * @throws Exception if either parameter is not in exact precision */
  public static Scalar of(Scalar a, Scalar b) {
    ExactScalarQ.require(a);
    ExactScalarQ.require(b);
    while (Scalars.nonZero(b)) {
      Scalar c = a;
      a = b;
      b = Mod.function(b).apply(c);
    }
    return StaticHelper.normalForm(a);
  }
}
