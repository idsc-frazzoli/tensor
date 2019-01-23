// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.function.BinaryOperator;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.sca.Mod;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/GCD.html">GCD</a> */
public enum GCD implements BinaryOperator<Scalar> {
  BIFUNCTION;
  // ---
  /** @param a integer
   * @param b integer
   * @return greatest common divider of a and b */
  @Override
  public Scalar apply(Scalar a, Scalar b) {
    return _of(ExactScalarQ.require(a), ExactScalarQ.require(b));
  }

  private static Scalar _of(Scalar a, Scalar b) {
    while (Scalars.nonZero(b)) {
      Scalar c = a;
      a = b;
      b = Mod.function(b).apply(c);
    }
    return StaticHelper.normalForm(a);
  }
}
