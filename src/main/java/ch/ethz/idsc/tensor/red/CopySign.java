// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.function.BinaryOperator;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.sca.Sign;
import ch.ethz.idsc.tensor.sca.SignInterface;

/** Composes a Scalar with the magnitude of a and the sign of b.
 * Function returns a or a.negate() depending on sign of a and b.
 * 
 * <p>The function appears
 * <ul>
 * <li>in the Fortran language and old literature
 * <li>{@link Math#copySign(double, double)}
 * <li><a href="http://en.cppreference.com/w/cpp/numeric/math/copysign">std::copysign</a>
 * </ul> */
public enum CopySign implements BinaryOperator<Scalar> {
  BIFUNCTION;
  // ---
  /** Hint:
   * implementation is not consistent with {@link Math#copySign(double, double)}
   * in the special case when the second argument b == -0.0.
   * The tensor library treats the case b == b.zero() as if b was positive.
   * 
   * @param a implements {@link SignInterface}
   * @param b implements {@link SignInterface}
   * @return {@link Scalar} of type of a with the magnitude of a and the sign of b */
  @Override
  public Scalar apply(Scalar a, Scalar b) {
    boolean sa = Sign.isPositiveOrZero(a);
    boolean sb = Sign.isPositiveOrZero(b);
    return sb ? (sa ? a : a.negate()) : (sa ? a.negate() : a);
  }

  public static Scalar of(Scalar a, Scalar b) {
    return BIFUNCTION.apply(a, b);
  }
}
