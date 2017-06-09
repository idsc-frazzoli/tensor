// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** implementation compliant to Java convention:
 * java.lang.Math.pow(0, 0) == 1
 * 
 * <p>not compliant with Mathematica
 * Mathematica::Power[0, 0] == 0^0 -> indeterminate
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Power.html">Power</a> */
public enum Power {
  ;
  // ---
  /** function attempts to give power as accurately as possible
   * and ultimately makes use of the identity
   * <code>x^y == Exp(y * Log(x))</code>
   * 
   * @param scalar
   * @param exponent
   * @return scalar ^ exponent */
  public static Scalar of(Scalar scalar, Scalar exponent) {
    if (scalar instanceof PowerInterface)
      return ((PowerInterface) scalar).power(exponent);
    if (Scalars.isZero(exponent))
      return RealScalar.ONE; // <- not generic
    throw TensorRuntimeException.of(scalar, exponent);
  }

  /** @param scalar
   * @param exponent
   * @return */
  public static Scalar of(Scalar scalar, Number exponent) {
    return of(scalar, RealScalar.of(exponent));
  }

  /** @param scalar
   * @param exponent
   * @return scalar ^ exponent */
  public static Scalar of(Number scalar, Number exponent) {
    return of(RealScalar.of(scalar), RealScalar.of(exponent));
  }

  /** @param exponent
   * @return function that maps a scalar to scalar ^ exponent */
  public static ScalarUnaryOperator function(Scalar exponent) {
    return scalar -> of(scalar, exponent);
  }

  /** @param exponent
   * @return function that maps a scalar to scalar ^ exponent */
  public static ScalarUnaryOperator function(Number exponent) {
    return function(RealScalar.of(exponent));
  }
}
