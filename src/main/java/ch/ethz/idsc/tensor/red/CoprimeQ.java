// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/CoprimeQ.html">CoprimeQ</a> */
public enum CoprimeQ {
  ;
  public static boolean of(Scalar a, Scalar b) {
    Scalar c = StaticHelper.normalForm(a.multiply(b));
    if (Scalars.isZero(a) && Scalars.isZero(b))
      return false;
    return LCM.of(a, b).equals(c);
  }
}
