// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.GaussScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** implementation compliant to Java convention:
 * java.lang.Math.pow(0, 0) == 1
 * 
 * <p>not compliant with Mathematica
 * Mathematica::Power[0, 0] == 0^0 -> indeterminate
 * 
 * <p>Power interprets {@link ZeroScalar} as {@link RealScalar}.
 * This leads to inconsistencies when using {@link Power} for
 * {@link GaussScalar}.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Power.html">Power</a> */
public class Power {
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
    if (exponent.equals(exponent.zero()))
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
  public static Function<Scalar, Scalar> function(Scalar exponent) {
    return new Function<Scalar, Scalar>() {
      @Override
      public Scalar apply(Scalar scalar) {
        return of(scalar, exponent);
      }
    };
  }

  /** @param exponent
   * @return function that maps a scalar to scalar ^ exponent */
  public static Function<Scalar, Scalar> function(Number exponent) {
    return function(RealScalar.of(exponent));
  }
}
