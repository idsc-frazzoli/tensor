// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.function.BiFunction;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.sca.SignInterface;

/** Composes a Scalar with the magnitude of a and the sign of b.
 * Function returns a or a.negate() depending on sign of a and b.
 * 
 * <p>The function appears in the Fortran language and old literature.
 * 
 * inspired by
 * <a href="http://en.cppreference.com/w/cpp/numeric/math/copysign">std::copysign</a> */
public enum CopySign implements BiFunction<Scalar, Scalar, Scalar> {
  BIFUNCTION;
  // ---
  /** @param a implements {@link SignInterface}
   * @param b implements {@link SignInterface}
   * @return {@link Scalar} of type of a with the magnitude of a and the sign of b */
  @Override
  public Scalar apply(Scalar a, Scalar b) {
    int sa = ((SignInterface) a).signInt();
    int sb = ((SignInterface) b).signInt();
    return 0 <= sb ? (0 <= sa ? a : a.negate()) : (0 <= sa ? a.negate() : a);
  }

  public static Scalar of(Scalar a, Scalar b) {
    return BIFUNCTION.apply(a, b);
  }
}
