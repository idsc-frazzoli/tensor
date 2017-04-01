// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigInteger;

/** an implementation is not required to support the representation of
 * Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, and Double.NaN */
public final class RationalScalar extends AbstractRealScalar implements ExactPrecision {
  // private because BigFraction has package visibility
  private static RealScalar _of(BigFraction bigFraction) {
    return bigFraction.num.equals(BigInteger.ZERO) ? //
        ZeroScalar.get() : new RationalScalar(bigFraction);
  }

  public static RealScalar of(BigInteger num, BigInteger den) {
    // if (den.signum() == 0)
    // return DoubleScalar.of(num.signum() == 0 ? Double.NaN : //
    // (0 < num.signum() ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY));
    return _of(BigFraction.of(num, den));
  }

  public static RealScalar of(long num, long den) {
    // if (den == 0)
    // return DoubleScalar.of(num == 0 ? Double.NaN : //
    // (0 < num ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY));
    return _of(BigFraction.of(num, den));
  }

  private final BigFraction bigFraction;

  /** private constructor is only called from of(...)
   * 
   * @param value */
  private RationalScalar(BigFraction bigFraction) {
    this.bigFraction = bigFraction;
  }

  public BigInteger getNumerator() {
    return bigFraction.num;
  }

  /** @return positive number */
  public BigInteger getDenominator() {
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
    if (getDenominator().equals(BigInteger.ONE)) {
      BigInteger bigInteger = getNumerator();
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

  @Override // from AbstractScalar
  protected Scalar plus(Scalar scalar) {
    if (scalar instanceof RationalScalar)
      return _of(bigFraction.add(((RationalScalar) scalar).bigFraction));
    return scalar.add(this);
  }

  @Override // from AbstractRealScalar
  protected boolean isPositive() {
    return 0 < bigFraction.num.signum();
  }

  @Override // from RealScalar
  public int compareTo(RealScalar realScalar) {
    if (realScalar instanceof RationalScalar) {
      RationalScalar rationalScalar = (RationalScalar) realScalar;
      BigInteger lhs = getNumerator().multiply(rationalScalar.getDenominator());
      BigInteger rhs = rationalScalar.getNumerator().multiply(getDenominator());
      return lhs.compareTo(rhs);
    }
    return -realScalar.compareTo(this);
  }

  @Override // from Object
  public int hashCode() {
    return bigFraction.hashCode();
  }

  @Override // from Object
  public boolean equals(Object object) {
    if (object instanceof RationalScalar)
      return bigFraction.equals(((RationalScalar) object).bigFraction);
    return object == null ? false : object.equals(this);
  }

  @Override // from Object
  public String toString() {
    return bigFraction.toCompactString();
  }
}
