// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Scalar;

/** <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/LCM.html">LCM</a> */
public enum LCM {
  ;
  /** @param a integer
   * @param b integer
   * @return least common multiple of a and b */
  public static Scalar of(Scalar a, Scalar b) {
    return a.divide(GCD.of(a, b)).multiply(b).abs();
  }
}
