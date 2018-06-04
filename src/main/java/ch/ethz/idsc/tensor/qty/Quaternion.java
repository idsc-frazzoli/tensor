// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.sca.ConjugateInterface;
import ch.ethz.idsc.tensor.sca.SqrtInterface;

/** Important: not all algorithms are tested for {@link Quaternion} input.
 * The consistent handling of the non-commutativity of the multiplication
 * may require significant modifications of the existing algorithms.
 * 
 * <p>Mathematica does not serve as a role model for quaternions. Their
 * corresponding functionality appears cumbersome and limited.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Quaternion.html">Quaternion</a> */
// EXPERIMENTAL
public interface Quaternion extends Scalar, //
    ConjugateInterface, SqrtInterface {
  /** @param re
   * @param im
   * @param jm
   * @param km
   * @return */
  static Scalar of(Number re, Number im, Number jm, Number km) {
    return of(RealScalar.of(re), RealScalar.of(im), RealScalar.of(jm), RealScalar.of(km));
  }

  /** @param re
   * @param im
   * @param jm
   * @param km
   * @return */
  static Scalar of(Scalar re, Scalar im, Scalar jm, Scalar km) {
    if (Scalars.isZero(jm) && Scalars.isZero(km))
      return ComplexScalar.of(re, im);
    return new QuaternionImpl(re, im, jm, km);
  }

  /** @return real part */
  Scalar re();

  /** @return coefficient of I */
  Scalar im();

  /** @return coefficient of J */
  Scalar jm();

  /** @return coefficient of K */
  Scalar km();
}
