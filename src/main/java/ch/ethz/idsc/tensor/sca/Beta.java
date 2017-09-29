// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;

/** <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Beta.html">Beta</a> */
public enum Beta {
  ;
  /** @param a
   * @param b
   * @return */
  public static Scalar of(Scalar a, Scalar b) {
    return Gamma.FUNCTION.apply(a).multiply(Gamma.FUNCTION.apply(b)) //
        .divide(Gamma.FUNCTION.apply(a.add(b)));
  }

  /** @param a
   * @param b
   * @return */
  public static Scalar of(Number a, Number b) {
    return of(RealScalar.of(a), RealScalar.of(b));
  }
}
