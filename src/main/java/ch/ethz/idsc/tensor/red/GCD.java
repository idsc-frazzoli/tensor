// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.IntegerQ;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.sca.Mod;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/GCD.html">GCD</a> */
public enum GCD {
  ;
  /** @param a integer
   * @param b integer
   * @return greatest common divider of a and b */
  public static Scalar of(Scalar a, Scalar b) {
    return _of(IntegerQ.require(a), IntegerQ.require(b)).abs();
  }

  private static Scalar _of(Scalar a, Scalar b) {
    return Scalars.isZero(b) ? a : _of(b, Mod.function(b).apply(a));
  }
}
