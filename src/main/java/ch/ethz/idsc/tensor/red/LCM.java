// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.function.BinaryOperator;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/LCM.html">LCM</a> */
public enum LCM implements BinaryOperator<Scalar> {
  BIFUNCTION;
  // ---
  /** @param a integer
   * @param b integer
   * @return least common multiple of a and b */
  @Override
  public Scalar apply(Scalar a, Scalar b) {
    Scalar gcd = GCD.BIFUNCTION.apply(a, b);
    if (Scalars.isZero(gcd))
      return a.multiply(b);
    return StaticHelper.normalForm(a.divide(gcd).multiply(b));
  }
}
