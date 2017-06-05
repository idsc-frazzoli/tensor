// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

import ch.ethz.idsc.tensor.sca.ExactNumberQInterface;
import ch.ethz.idsc.tensor.sca.NInterface;
import ch.ethz.idsc.tensor.sca.Sqrt;

/** an implementation is not required to support the representation of
 * Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, and Double.NaN */
public final class RationalScalar extends AbstractRealScalar implements //
    ExactNumberQInterface, NInterface {
  // private because BigFraction has package visibility
  private static RealScalar _of(BigFraction bigFraction) {
    return new RationalScalar(bigFraction);
  }

  /** @param num
   * @param den
   * @return scalar encoding the exact fraction num / den */
  public static RealScalar of(BigInteger num, BigInteger den) {
    return _of(BigFraction.of(num, den));
  }

  /** @param num
   * @param den
   * @return scalar encoding the exact fraction num / den */
  public static RealScalar of(long num, long den) {
    return _of(BigFraction.of(num, den));
  }

  private final BigFraction bigFraction;

  /** private constructor is only called from of(...)
   * 
   * @param value */
  private RationalScalar(BigFraction bigFraction) {
    this.bigFraction = bigFraction;
  }

  /** @return numerator as {@link BigInteger} */
  public BigInteger numerator() {
    return bigFraction.num;
  }

  /** @return denominator as {@link BigInteger},
   * the denominator of a {@link RationalScalar} is always positive */
  public BigInteger denominator() {
    return bigFraction.den;
  }

  @Override // from Scalar
  public RealScalar invert() {
    return _of(bigFraction.invert());
  }

  @Override // from Scalar
  public RealScalar negate() {
    return _of(bigFraction.negate());
  }

  @Override // from Scalar
  public Scalar multiply(Scalar scalar) {
    if (scalar instanceof RationalScalar)
      return _of(bigFraction.multiply(((RationalScalar) scalar).bigFraction));
    return scalar.multiply(this);
  }

  @Override // from Scalar
  public Scalar zero() {
    return RealScalar.ZERO;
  }

  @Override // from Scalar
  public Number number() {
    if (IntegerQ.of(this)) {
      BigInteger bigInteger = numerator();
      try {
        return bigInteger.intValueExact();
      } catch (Exception exception) {
        // ---
      }
      try {
        return bigInteger.longValueExact();
      } catch (Exception exception) {
        // ---
      }
      return bigInteger;
    }
    return toBigDecimal(MathContext.DECIMAL64).doubleValue();
  }

  // EXPERIMENTAL
  public BigDecimal toBigDecimal(int scale, RoundingMode roundingMode) {
    return new BigDecimal(numerator()).divide(new BigDecimal(denominator()), scale, roundingMode);
  }

  public BigDecimal toBigDecimal(MathContext mathContext) {
    return new BigDecimal(numerator()).divide(new BigDecimal(denominator()), mathContext);
  }

  @Override // from AbstractScalar
  protected Scalar plus(Scalar scalar) {
    if (scalar instanceof RationalScalar)
      return _of(bigFraction.add(((RationalScalar) scalar).bigFraction));
    return scalar.add(this);
  }

  @Override // from AbstractRealScalar
  protected boolean isNonNegative() {
    return 0 <= bigFraction.num.signum();
  }

  @Override // from RoundInterface
  public Scalar round() {
    return of(toBigDecimal(0, RoundingMode.HALF_UP).toBigIntegerExact(), BigInteger.ONE);
  }

  /** Example: sqrt(16/25) == 4/5
   * 
   * @return {@link RationalScalar} precision if numerator and denominator are both squares */
  @Override // from AbstractRealScalar
  public Scalar sqrt() {
    try {
      boolean pos = isNonNegative();
      BigInteger sqrtnum = Sqrt.of(pos ? bigFraction.num : bigFraction.num.negate());
      BigInteger sqrtden = Sqrt.of(bigFraction.den);
      return pos ? of(sqrtnum, sqrtden) : ComplexScalar.of(ZERO, of(sqrtnum, sqrtden));
    } catch (Exception exception) {
      // ---
    }
    return super.sqrt();
  }

  @Override // from AbstractRealScalar
  public Scalar power(Scalar exponent) {
    if (IntegerQ.of(exponent)) {
      RationalScalar exp = (RationalScalar) exponent;
      try {
        // intValueExact throws an exception when exp > Integer.MAX_VALUE
        int expInt = exp.numerator().intValueExact();
        if (0 <= expInt)
          return RationalScalar.of( //
              numerator().pow(expInt), //
              denominator().pow(expInt));
        return RationalScalar.of( //
            denominator().pow(-expInt), //
            numerator().pow(-expInt));
      } catch (Exception exception) {
        return Scalars.binaryPower(RealScalar.ONE).apply(this, exp.numerator());
      }
    }
    return super.power(exponent);
  }

  @Override // from ExactNumberQInterface
  public boolean isExactNumber() {
    return true;
  }

  @Override // from NInterface
  public Scalar n() {
    return DoubleScalar.of(toBigDecimal(MathContext.DECIMAL64).doubleValue());
  }

  @Override // from RealScalar
  public int compareTo(Scalar scalar) {
    if (scalar instanceof RationalScalar) {
      RationalScalar rationalScalar = (RationalScalar) scalar;
      BigInteger lhs = numerator().multiply(rationalScalar.denominator());
      BigInteger rhs = rationalScalar.numerator().multiply(denominator());
      return lhs.compareTo(rhs);
    }
    @SuppressWarnings("unchecked")
    Comparable<Scalar> comparable = (Comparable<Scalar>) scalar;
    return -comparable.compareTo(this);
  }

  @Override // from AbstractScalar
  public int hashCode() {
    return bigFraction.hashCode();
  }

  @Override // from AbstractScalar
  public boolean equals(Object object) {
    if (object instanceof RationalScalar) {
      RationalScalar rationalScalar = (RationalScalar) object;
      return bigFraction.equals(rationalScalar.bigFraction);
    }
    return object == null ? false : object.equals(this);
  }

  @Override // from AbstractScalar
  public String toString() {
    return bigFraction.toCompactString();
  }
}
