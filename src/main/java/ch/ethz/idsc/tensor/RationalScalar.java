// code by jph
package ch.ethz.idsc.tensor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

import ch.ethz.idsc.tensor.sca.ExactScalarQInterface;
import ch.ethz.idsc.tensor.sca.NInterface;

/** a RationalScalar corresponds to an element from the field of rational numbers.
 * 
 * a RationalScalar represents an integer fraction, for instance 17/42, or -6/1.
 * 
 * zero().reciprocal() throws a {@link ArithmeticException}. */
public final class RationalScalar extends AbstractRealScalar implements //
    ExactScalarQInterface, NInterface, Serializable {
  /** rational number {@code 1/2} with decimal value {@code 0.5} */
  public static final Scalar HALF = of(1, 2);

  /** @param num
   * @param den
   * @return scalar encoding the exact fraction num / den */
  public static Scalar of(BigInteger num, BigInteger den) {
    return new RationalScalar(BigFraction.of(num, den));
  }

  /** @param num
   * @param den
   * @return scalar encoding the exact fraction num / den */
  public static Scalar of(long num, long den) {
    return new RationalScalar(BigFraction.of(num, den));
  }

  private final BigFraction bigFraction;

  /** private constructor is only called from of(...)
   * 
   * @param value */
  private RationalScalar(BigFraction bigFraction) {
    this.bigFraction = bigFraction;
  }

  /***************************************************/
  @Override // from Scalar
  public Scalar negate() {
    return new RationalScalar(bigFraction.negate());
  }

  @Override // from Scalar
  public Scalar multiply(Scalar scalar) {
    if (scalar instanceof RationalScalar) {
      RationalScalar rationalScalar = (RationalScalar) scalar;
      return new RationalScalar(bigFraction.multiply(rationalScalar.bigFraction));
    }
    return scalar.multiply(this);
  }

  @Override // from AbstractScalar
  public Scalar divide(Scalar scalar) {
    if (scalar instanceof RationalScalar) {
      // default implementation in AbstractScalar uses 2x gcd
      RationalScalar rationalScalar = (RationalScalar) scalar;
      return new RationalScalar(bigFraction.divide(rationalScalar.bigFraction));
    }
    return scalar.under(this);
  }

  @Override // from AbstractScalar
  public Scalar under(Scalar scalar) {
    if (scalar instanceof RationalScalar) {
      // default implementation in AbstractScalar uses 2x gcd
      RationalScalar rationalScalar = (RationalScalar) scalar;
      return new RationalScalar(rationalScalar.bigFraction.divide(bigFraction));
    }
    return scalar.divide(this);
  }

  @Override // from Scalar
  public Scalar reciprocal() {
    return new RationalScalar(bigFraction.reciprocal());
  }

  @Override // from Scalar
  public Number number() {
    if (bigFraction.isInteger()) { // IntegerQ.of(this)
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

  @Override // from Scalar
  public Scalar zero() {
    return ZERO;
  }

  /***************************************************/
  @Override // from AbstractScalar
  protected Scalar plus(Scalar scalar) {
    if (scalar instanceof RationalScalar)
      return new RationalScalar(bigFraction.add(((RationalScalar) scalar).bigFraction));
    return scalar.add(this);
  }

  /***************************************************/
  @Override // from RoundingInterface
  public Scalar ceiling() {
    return of(toBigDecimal(0, RoundingMode.CEILING).toBigIntegerExact(), BigInteger.ONE);
  }

  @Override // from Comparable<Scalar>
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

  @Override // from RoundingInterface
  public Scalar floor() {
    return of(toBigDecimal(0, RoundingMode.FLOOR).toBigIntegerExact(), BigInteger.ONE);
  }

  @Override // from ExactScalarQInterface
  public boolean isExactScalar() {
    return true;
  }

  @Override // from NInterface
  public Scalar n() {
    return DoubleScalar.of(toBigDecimal(MathContext.DECIMAL64).doubleValue());
  }

  @Override // from NInterface
  public Scalar n(MathContext mathContext) {
    return DecimalScalar.of(toBigDecimal(mathContext));
  }

  @Override // from AbstractRealScalar
  public Scalar power(Scalar exponent) {
    if (IntegerQ.of(exponent)) {
      RationalScalar exp = (RationalScalar) exponent;
      try {
        // intValueExact throws an exception when exp > Integer.MAX_VALUE
        int expInt = Scalars.intValueExact(exp);
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

  @Override // from RoundingInterface
  public Scalar round() {
    return of(toBigDecimal(0, RoundingMode.HALF_UP).toBigIntegerExact(), BigInteger.ONE);
  }

  @Override // from SignInterface
  public int signInt() {
    return bigFraction.signum();
  }

  /** Example: sqrt(16/25) == 4/5
   * 
   * @return {@link RationalScalar} precision if numerator and denominator are both squares */
  @Override // from AbstractRealScalar
  public Scalar sqrt() {
    try {
      boolean isNonNegative = isNonNegative();
      BigInteger sqrtnum = BigIntegerMath.sqrt(isNonNegative ? numerator() : numerator().negate());
      BigInteger sqrtden = BigIntegerMath.sqrt(denominator());
      return isNonNegative ? of(sqrtnum, sqrtden) : ComplexScalarImpl.of(ZERO, of(sqrtnum, sqrtden));
    } catch (Exception exception) {
      // ---
    }
    return super.sqrt();
  }

  /***************************************************/
  /** @return numerator as {@link BigInteger} */
  public BigInteger numerator() {
    return bigFraction.numerator();
  }

  /** @return denominator as {@link BigInteger},
   * the denominator of a {@link RationalScalar} is always positive */
  public BigInteger denominator() {
    return bigFraction.denominator();
  }

  private BigDecimal toBigDecimal(int scale, RoundingMode roundingMode) {
    return new BigDecimal(numerator()).divide(new BigDecimal(denominator()), scale, roundingMode);
  }

  /* package */ BigDecimal toBigDecimal(MathContext mathContext) {
    return new BigDecimal(numerator()).divide(new BigDecimal(denominator()), mathContext);
  }

  /* package */ boolean isInteger() {
    return bigFraction.isInteger();
  }

  /***************************************************/
  @Override // from AbstractScalar
  public int hashCode() {
    return bigFraction.hashCode();
  }

  @Override // from AbstractScalar
  public boolean equals(Object object) {
    if (object instanceof RationalScalar) {
      RationalScalar rationalScalar = (RationalScalar) object;
      return bigFraction._equals(rationalScalar.bigFraction);
    }
    return Objects.nonNull(object) && object.equals(this);
  }

  @Override // from AbstractScalar
  public String toString() {
    return bigFraction.toCompactString();
  }
}
