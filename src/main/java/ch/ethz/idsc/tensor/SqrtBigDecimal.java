// code adapted by jph
// inspired by Luciano Culacciatti
// http://www.codeproject.com/Tips/257031/Implementing-SqrtRoot-in-BigDecimal 
package ch.ethz.idsc.tensor;

import java.math.BigDecimal;
import java.math.MathContext;

/* package */ enum SqrtBigDecimal {
  ;
  private static final BigDecimal TWO = BigDecimal.valueOf(2);

  /** computation of square-root using Newton iteration
   * 
   * @param square
   * @param mathContext
   * @return root of given square */
  static BigDecimal of(BigDecimal square, MathContext mathContext) {
    int signum = square.signum();
    if (signum == 0)
      return BigDecimal.ZERO;
    if (signum < 0)
      throw new RuntimeException("Sqrt[" + square + "]");
    BigDecimal xn0 = BigDecimal.ZERO;
    BigDecimal xn1 = BigDecimal.ONE;
    while (xn0.compareTo(xn1) != 0) {
      xn0 = xn1;
      BigDecimal fx = xn0.pow(2).subtract(square);
      BigDecimal fpx = xn0.multiply(TWO);
      xn1 = fx.divide(fpx, mathContext);
      xn1 = xn0.subtract(xn1, mathContext);
    }
    return xn1;
  }
}
