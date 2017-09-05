// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigInteger;

/** implementation is standalone */
/* package */ enum BigIntegerMath {
  ;
  /** @param value
   * @return exact root of value
   * @throws IllegalArgumentException if value is not a square number */
  public static BigInteger sqrt(BigInteger value) {
    BigInteger root = sqrtApproximation(value);
    if (root.multiply(root).equals(value))
      return root;
    throw new IllegalArgumentException(value.toString()); // value is not square
  }

  /** @param value
   * @return approximation to sqrt of value, exact root if input value is square number */
  // https://gist.github.com/JochemKuijpers/cd1ad9ec23d6d90959c549de5892d6cb
  private static BigInteger sqrtApproximation(BigInteger value) {
    BigInteger a = BigInteger.ONE;
    BigInteger b = value.shiftRight(5).add(BigInteger.valueOf(8));
    while (0 <= b.compareTo(a)) {
      BigInteger mid = a.add(b).shiftRight(1);
      if (0 < mid.multiply(mid).compareTo(value))
        b = mid.subtract(BigInteger.ONE);
      else
        a = mid.add(BigInteger.ONE);
    }
    return a.subtract(BigInteger.ONE);
  }
}
