// code by jph
package ch.ethz.idsc.tensor;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

/** immutable integer fraction in normal form, i.e. denominator is strictly positive */
/* package */ final class BigFraction implements Serializable {
  private static final String DIVIDE = "/";

  /** @param value
   * @return big fraction that represents the integer value */
  public static BigFraction integer(long value) {
    return new BigFraction( //
        BigInteger.valueOf(value), //
        BigInteger.ONE);
  }

  /** @param value
   * @return big fraction that represents the integer value */
  public static BigFraction integer(BigInteger value) {
    return new BigFraction( //
        value, //
        BigInteger.ONE);
  }

  /** @param num numerator
   * @param den denominator non-zero
   * @return
   * @throws {@link ArithmeticException} if den is zero */
  public static BigFraction of(long num, long den) {
    return of( //
        BigInteger.valueOf(num), //
        BigInteger.valueOf(den));
  }

  /** @param num numerator
   * @param den denominator non-zero
   * @return
   * @throws {@link ArithmeticException} if den is zero */
  public static BigFraction of(BigInteger num, BigInteger den) {
    if (den.signum() == 0)
      throw new ArithmeticException(num + DIVIDE + den);
    return create(num, den);
  }

  /** @param num numerator
   * @param den denominator guaranteed to be non-zero
   * @return */
  private static BigFraction create(BigInteger num, BigInteger den) {
    BigInteger gcd = num.gcd(den);
    BigInteger res = den.divide(gcd);
    return res.signum() == 1 //
        ? new BigFraction(num.divide(gcd), res) //
        : new BigFraction(num.divide(gcd).negate(), res.negate());
  }

  // ---
  /** numerator */
  private final BigInteger num;
  /** denominator (always greater than zero) */
  private final BigInteger den;

  private BigFraction(BigInteger num, BigInteger den) {
    this.num = num;
    this.den = den;
  }

  public BigFraction negate() {
    return new BigFraction(num.negate(), den);
  }

  /** uses gcd of the denominators (better only in special cases than the straight forward formula)
   * 
   * @param bigFraction
   * @return */
  public BigFraction add(BigFraction bigFraction) {
    return create( //
        num.multiply(bigFraction.den).add(bigFraction.num.multiply(den)), //
        den.multiply(bigFraction.den)); // denominators are non-zero
  }

  /** multiplication, applies gcd 1x
   * 
   * @param bigFraction
   * @return */
  public BigFraction multiply(BigFraction bigFraction) {
    return create( //
        num.multiply(bigFraction.num), //
        den.multiply(bigFraction.den)); // denominators are non-zero
  }

  /** division, applies gcd 1x
   * 
   * @param bigFraction
   * @return this / bigFraction
   * @throws Exception if given bigFraction is zero */
  public BigFraction divide(BigFraction bigFraction) {
    if (bigFraction.signum() == 0)
      throw new ArithmeticException(bigFraction.den + DIVIDE + bigFraction.num);
    return create( //
        num.multiply(bigFraction.den), //
        den.multiply(bigFraction.num));
  }

  /** @return reciprocal == den/num */
  public BigFraction reciprocal() {
    int signum = signum();
    if (signum == 0)
      throw new ArithmeticException(den + DIVIDE + num);
    return signum == 1 //
        ? new BigFraction(den, num) //
        : new BigFraction(den.negate(), num.negate()); //
  }

  /** @return true if the fraction encodes an integer, i.e. if the denominator equals 1 */
  public boolean isInteger() {
    return den.equals(BigInteger.ONE);
  }

  /** @return -1, 0 or 1 as the value of this BigFraction is negative, zero or positive */
  public int signum() {
    return num.signum();
  }

  /** @return numerator of this BigFraction */
  public BigInteger numerator() {
    return num;
  }

  /** @return denominator of this BigFraction */
  public BigInteger denominator() {
    return den;
  }

  /** @param bigFraction non-null
   * @return */
  boolean _equals(BigFraction bigFraction) {
    return num.equals(bigFraction.num) //
        && den.equals(bigFraction.den); // sufficient since in normal form
  }

  /***************************************************/
  @Override // from Object
  public String toString() {
    return isInteger() //
        ? num.toString()
        : num.toString() + DIVIDE + den.toString();
  }

  @Override // from Object
  public int hashCode() {
    return Objects.hash(num, den);
  }
  // intentional: no override of Object::equals(Object)
}
