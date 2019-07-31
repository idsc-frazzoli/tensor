// code by jph
package ch.ethz.idsc.tensor.num;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.sca.Mod;

/** implementation is consistent with Mathematica::Divisible
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Divisible.html">Divisible</a> */
public enum Divisible {
  ;
  /** @param n numerator in exact precision
   * @param m denominator in exact precision
   * @return Mod[n, m] == 0
   * @throws Exception if given n or m are not in exact precision */
  public static boolean of(Scalar n, Scalar m) {
    return Scalars.isZero(ExactScalarQ.require(Mod.function(m).apply(n)));
  }
}
