// code by jph
package ch.ethz.idsc.tensor.num;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/LCM.html">LCM</a> */
public enum LCM {
  ;
  /** @param a integer
   * @param b integer
   * @return least common multiple of a and b */
  public static Scalar of(Scalar a, Scalar b) {
    Scalar gcd = GCD.of(a, b);
    if (Scalars.isZero(gcd))
      return a.multiply(b);
    return StaticHelper.normalForm(a.divide(gcd).multiply(b));
  }
}
