// code by jph
package ch.ethz.idsc.tensor.sca;

import java.math.BigInteger;
import java.util.function.Function;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.ZeroScalar;

/** not compliant with Mathematica
 * 
 * Mathematica: 0^0 -> indeterminate
 * Java: Math.pow(0, 0) == 1
 * 
 * inspired by
 * <a href="https://reference.wolfram.com/language/ref/Power.html">Power</a> */
public class Power implements Function<Scalar, Scalar> {
  /** @param scalar
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
    // TODO complex scalars
    return RealScalar.of(Math.pow(scalar.number().doubleValue(), exponent.number().doubleValue()));
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

  public static Function<Scalar, Scalar> of(Scalar exponent) {
    return new Power(exponent);
  }

  private final Scalar exponent;

  private Power(Scalar exponent) {
    this.exponent = exponent;
  }

  @Override
  public Scalar apply(Scalar scalar) {
    return of(scalar, exponent);
  }
}
