// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.function.BiFunction;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;

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
  /** @param a is {@link RealScalar}
   * @param b is {@link RealScalar}
   * @return {@link RealScalar} with the magnitude of a and the sign of b */
  @Override
  public Scalar apply(Scalar a, Scalar b) {
    int sa = ((RealScalar) a).signInt();
    int sb = ((RealScalar) b).signInt();
    return 0 <= sb ? (0 <= sa ? a : a.negate()) : (0 <= sa ? a.negate() : a);
  }
}
