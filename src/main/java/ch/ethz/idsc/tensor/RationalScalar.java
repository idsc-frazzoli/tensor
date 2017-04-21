// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import ch.ethz.idsc.tensor.sca.Sqrt;

/** an implementation is not required to support the representation of
 * Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, and Double.NaN */
public final class RationalScalar extends AbstractRealScalar {
  // private because BigFraction has package visibility
  private static RealScalar _of(BigFraction bigFraction) {
    return bigFraction.num.equals(BigInteger.ZERO) ? //
        ZeroScalar.get() : new RationalScalar(bigFraction);
  }

  public static RealScalar of(BigInteger num, BigInteger den) {
    return _of(BigFraction.of(num, den));
  }

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

  public BigInteger numerator() {
    return bigFraction.num;
  }

  /** @return positive number */
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
  public Number number() {
    if (isInteger()) {
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
    return bigFraction.doubleValue();
  }

  // EXPERIMENTAL
  public BigDecimal toBigDecimal(int scale, RoundingMode roundingMode) {
    return new BigDecimal(numerator()).divide(new BigDecimal(denominator()), scale, roundingMode);
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

  /** Example: sqrt(16/25) == 4/5
   * 
   * @return {@link RationalScalar} precision if numerator and denominator are both squares */
  @Override
  public Scalar sqrt() {
    try {
      boolean pos = isNonNegative();
      BigInteger sqrtnum = Sqrt.of(pos ? bigFraction.num : bigFraction.num.negate());
      BigInteger sqrtden = Sqrt.of(bigFraction.den);
      return pos ? of(sqrtnum, sqrtden) : ComplexScalar.of(ZeroScalar.get(), of(sqrtnum, sqrtden));
    } catch (Exception exception) {
      // ---
    }
    return super.sqrt();
  }

  @Override // from NInterface
  public Scalar n() {
    return DoubleScalar.of(bigFraction.doubleValue());
  }

  @Override // from RealScalar
  public int compareTo(Scalar scalar) {
    if (scalar instanceof RationalScalar) {
      RationalScalar rationalScalar = (RationalScalar) scalar;
      BigInteger lhs = numerator().multiply(rationalScalar.denominator());
      BigInteger rhs = rationalScalar.numerator().multiply(denominator());
      return lhs.compareTo(rhs);
    }
    if (scalar instanceof ZeroScalar)
      return signInt();
    @SuppressWarnings("unchecked")
    Comparable<Scalar> comparable = (Comparable<Scalar>) scalar;
    return -comparable.compareTo(this);
  }

  /** @return true if denominator equals 1 */
  public boolean isInteger() {
    return bigFraction.isInteger();
  }

  @Override // from AbstractScalar
  public int hashCode() {
    return bigFraction.hashCode();
  }

  @Override // from AbstractScalar
  public boolean equals(Object object) {
    if (object instanceof RationalScalar)
      return bigFraction.equals(((RationalScalar) object).bigFraction);
    return object == null ? false : object.equals(this);
  }

  @Override // from AbstractScalar
  public String toString() {
    return bigFraction.toCompactString();
  }
}
