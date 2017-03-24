// code by jph
package ch.ethz.idsc.tensor;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

/** integer fraction in normal form non-degenerate, i.e. denominator != 0 immutable */
/* package */ final class BigFraction implements Serializable {
  public static BigFraction of(long num, long den) {
    return of(BigInteger.valueOf(num), BigInteger.valueOf(den));
  }

  public static BigFraction of(BigInteger num, BigInteger den) {
    return new BigFraction(num, den);
  }

  /** numerator */
  public final BigInteger num;
  /** denominator (always greater than zero) */
  public final BigInteger den;

  /** @param num
   * @param den has to be non-zero
   * @throws {@link ArithmeticException} if den is zero */
  private BigFraction(final BigInteger num, final BigInteger den) {
    if (den.equals(BigInteger.ZERO))
      throw new ArithmeticException(num + "/" + den);
    BigInteger gcd = num.gcd(den);
    BigInteger res = den.divide(gcd);
    if (0 < res.signum()) {
      this.num = num.divide(gcd);
      this.den = res;
    } else {
      this.num = num.divide(gcd).negate();
      this.den = res.negate();
    }
  }

  public BigFraction negate() {
    return of(num.negate(), den);
  }

  /** uses gcd of the denominators (better only in special cases than the straight forward formula)
   * 
   * @param bigFraction
   * @return */
  public BigFraction add(BigFraction bigFraction) {
    return of( //
        num.multiply(bigFraction.den).add(bigFraction.num.multiply(den)), //
        den.multiply(bigFraction.den));
  }

  /** multiplication
   * applies gcd 1x
   * 
   * @param bigFraction
   * @return */
  public BigFraction multiply(BigFraction bigFraction) {
    return of(num.multiply(bigFraction.num), den.multiply(bigFraction.den));
  }

  /** @return reciprocal == den/num */
  public BigFraction invert() {
    return of(den, num);
  }

  public double doubleValue() {
    return num.doubleValue() / den.doubleValue();
  }

  public String toCompactString() {
    StringBuilder stringBuilder = new StringBuilder(num.toString());
    if (!den.equals(BigInteger.ONE))
      stringBuilder.append("/" + den.toString());
    return stringBuilder.toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(num, den);
  }

  @Override
  public boolean equals(Object object) {
    if (object == null)
      return false;
    BigFraction bigFraction = (BigFraction) object;
    return num.equals(bigFraction.num) && den.equals(bigFraction.den); // sufficient since in normal form
  }
}
