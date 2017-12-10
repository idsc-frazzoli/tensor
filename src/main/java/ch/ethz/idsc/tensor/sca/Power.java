// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** Power exponentiates a given scalar by an exponent.
 * The scalar type is required to implement {@link PowerInterface}
 * in order for the operation to succeed.
 * 
 * <p>The implementation is compliant to the Java convention:
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
    if (scalar instanceof PowerInterface) {
      PowerInterface powerInterface = (PowerInterface) scalar;
      return powerInterface.power(exponent);
    }
    throw TensorRuntimeException.of(scalar, exponent);
  }

  /** @param scalar
   * @param exponent
   * @return scalar ^ exponent */
  public static Scalar of(Scalar scalar, Number exponent) {
    return of(scalar, RealScalar.of(exponent));
  }

  /** @param number
   * @param exponent
   * @return scalar ^ exponent */
  public static Scalar of(Number number, Scalar exponent) {
    return of(RealScalar.of(number), exponent);
  }

  /** @param number
   * @param exponent
   * @return number ^ exponent */
  public static Scalar of(Number number, Number exponent) {
    return of(RealScalar.of(number), RealScalar.of(exponent));
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
