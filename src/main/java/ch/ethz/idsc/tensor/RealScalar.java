// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigInteger;

public interface RealScalar extends Scalar, Comparable<RealScalar> {
  /** real scalar 1 in {@link ExactPrecision} */
  public static final RealScalar ONE = RealScalar.of(1);
  /** real scalar that encodes Infinity. value is backed by Double.POSITIVE_INFINITY */
  public static final RealScalar POSITIVE_INFINITY = of(Double.POSITIVE_INFINITY);
  /** real scalar that encodes -Infinity. value is backed by Double.NEGATIVE_INFINITY */
  public static final RealScalar NEGATIVE_INFINITY = of(Double.NEGATIVE_INFINITY);

  /** @param number
   * @return scalar with best possible accuracy to describe number */
  public static RealScalar of(Number number) {
    if (number instanceof Integer || number instanceof Long)
      return RationalScalar.of(number.longValue(), 1);
    if (number instanceof Float || number instanceof Double)
      return DoubleScalar.of(number.doubleValue());
    if (number instanceof BigInteger)
      return RationalScalar.of((BigInteger) number, BigInteger.ONE);
    throw new IllegalArgumentException();
  }

  /***************************************************/
  /** @param a
   * @param b
   * @return the smaller one among a and b */
  public static RealScalar min(RealScalar a, RealScalar b) {
    return a.compareTo(b) > 0 ? b : a;
  }

  /** @param a
   * @param b
   * @return the greater one among a and b */
  public static RealScalar max(RealScalar a, RealScalar b) {
    return a.compareTo(b) < 0 ? b : a;
  }

  /***************************************************/
  @Override // from Scalar
  RealScalar negate(); // used by abs()

  /** abs() returns this or this.negate() depending on whichever is non-negative */
  @Override // from Scalar
  RealScalar abs();

  @Override // from Scalar
  RealScalar absSquared();

  @Override // from Scalar
  RealScalar conjugate();

  /***************************************************/
  /** @return gives -1, 0, or 1 depending on whether this is negative, zero, or positive. */
  int signInt();
}
