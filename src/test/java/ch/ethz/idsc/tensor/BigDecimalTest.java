// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

import junit.framework.TestCase;

public class BigDecimalTest extends TestCase {
  public void testPrecision() {
    BigDecimal value = new BigDecimal(new BigInteger("12333"), new MathContext(100, RoundingMode.HALF_EVEN));
    value = new BigDecimal("12333", new MathContext(100, RoundingMode.HALF_EVEN));
    value.precision();
    int precision = value.precision();
    assertTrue(0 < precision);
  }
}
