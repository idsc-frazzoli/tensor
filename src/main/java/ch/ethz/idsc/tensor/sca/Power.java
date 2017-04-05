// code by jph
package ch.ethz.idsc.tensor.sca;

import java.math.BigInteger;
import java.util.function.Function;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.ZeroScalar;

/** implementation compliant to Java convention:
 * java.lang.Math.pow(0, 0) == 1
 * 
 * <p>not compliant with Mathematica
 * Mathematica::Power[0, 0] == 0^0 -> indeterminate
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
    if (exponent instanceof ZeroScalar)
      return RealScalar.ONE;
    if (scalar instanceof RationalScalar && exponent instanceof RationalScalar) {
      RationalScalar exp = (RationalScalar) exponent;
      if (exp.denominator().equals(BigInteger.ONE)) {
        RationalScalar rational = (RationalScalar) scalar;
        int expInt = exp.numerator().intValue();
        if (0 < expInt)
          return RationalScalar.of( //
              rational.numerator().pow(expInt), //
              rational.denominator().pow(expInt));
        return RationalScalar.of( //
            rational.denominator().pow(-expInt), //
            rational.numerator().pow(-expInt));
      }
    }
    if (scalar instanceof RealScalar && exponent instanceof RealScalar)
      return RealScalar.of(Math.pow(scalar.number().doubleValue(), exponent.number().doubleValue()));
    return Exp.function.apply(exponent.multiply(Log.function.apply(scalar)));
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
