// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Boole.html">Boole</a> */
public enum Boole {
  ;
  /** @param value
   * @return 1 if given value is true, else 0 */
  public static Scalar of(boolean value) {
    return value //
        ? RealScalar.ONE
        : RealScalar.ZERO;
  }
}
