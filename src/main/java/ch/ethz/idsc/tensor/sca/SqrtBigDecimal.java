// code by Luciano Culacciatti
// http://www.codeproject.com/Tips/257031/Implementing-SqrtRoot-in-BigDecimal 
// adapted by jph
package ch.ethz.idsc.tensor.sca;

import java.math.BigDecimal;
import java.math.RoundingMode;

/* package */ final class SqrtBigDecimal {
  final int digits;
  final BigDecimal precision;

  SqrtBigDecimal(int digits) {
    this.digits = digits;
    precision = BigDecimal.ONE.divide(new BigDecimal(10).pow(digits));
  }

  BigDecimal newtonRaphson(BigDecimal c) {
    return newtonRaphson(c, BigDecimal.ONE);
  }

  BigDecimal newtonRaphson(BigDecimal c, BigDecimal xn) {
    while (true) {
      BigDecimal fx = xn.pow(2).add(c.negate());
      BigDecimal fpx = xn.multiply(new BigDecimal(2));
      BigDecimal xn1 = fx.divide(fpx, 2 * digits, RoundingMode.HALF_DOWN);
      xn1 = xn.add(xn1.negate());
      BigDecimal currentPrecision = xn1.pow(2).subtract(c).abs();
      if (currentPrecision.compareTo(precision) <= -1)
        return xn1;
      xn = xn1;
    }
  }
}
