// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

import junit.framework.TestCase;

public class SqrtBigDecimalTest extends TestCase {
  private static void _check(BigDecimal bd1, MathContext mathContext) {
    BigDecimal rt1 = SqrtBigDecimal.of(bd1, MathContext.DECIMAL32);
    assertEquals(rt1.multiply(rt1, mathContext).subtract(bd1).compareTo(BigDecimal.ZERO), 0);
  }

  public void testSimple() {
    _check(new BigDecimal("4767655423.1", new MathContext(2, RoundingMode.HALF_EVEN)), MathContext.DECIMAL32);
    _check(new BigDecimal("5423.1", new MathContext(2, RoundingMode.HALF_EVEN)), MathContext.DECIMAL32);
    _check(new BigDecimal("0.000001235423", new MathContext(2, RoundingMode.HALF_EVEN)), MathContext.DECIMAL32);
    _check(new BigDecimal("0.000000000000000000000000000000000001"), MathContext.DECIMAL128);
    _check(new BigDecimal("0"), MathContext.DECIMAL128);
    _check(BigDecimal.ZERO, MathContext.DECIMAL128);
    _check(new BigDecimal("1"), MathContext.DECIMAL128);
    _check(BigDecimal.ONE, MathContext.DECIMAL128);
  }

  public void testExact() {
    BigDecimal bd1 = new BigDecimal(BigInteger.valueOf(25 * 25));
    BigDecimal rt1 = SqrtBigDecimal.of(bd1, MathContext.DECIMAL32);
    rt1.equals(RationalScalar.of(25, 1)); // gives false
    assertEquals(rt1.compareTo(new BigDecimal("25")), 0);
  }

  public void testTwo() {
    BigDecimal bd1 = new BigDecimal("2");
    BigDecimal rt1 = SqrtBigDecimal.of(bd1, new MathContext(100, RoundingMode.HALF_EVEN));
    // mathematica N[Sqrt[2], 100] gives
    String m = "1.414213562373095048801688724209698078569671875376948073176679737990732478462107038850387534327641573";
    assertEquals(rt1.toString(), m);
  }

  public void testZero() {
    BigDecimal bd1 = new BigDecimal("0");
    BigDecimal rt1 = SqrtBigDecimal.of(bd1, MathContext.DECIMAL64);
    assertEquals(rt1.compareTo(BigDecimal.ZERO), 0);
  }

  public void testNegative() {
    try {
      SqrtBigDecimal.of(new BigDecimal("-2340"), MathContext.DECIMAL64);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
