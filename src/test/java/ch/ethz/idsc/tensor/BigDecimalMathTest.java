// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Cos;
import ch.ethz.idsc.tensor.sca.Cosh;
import ch.ethz.idsc.tensor.sca.Sin;
import ch.ethz.idsc.tensor.sca.Sinh;
import junit.framework.TestCase;

public class BigDecimalMathTest extends TestCase {
  private static void _check(BigDecimal bd1, MathContext mathContext) {
    BigDecimal rt1 = BigDecimalMath.sqrt(bd1, MathContext.DECIMAL32);
    assertEquals(rt1.multiply(rt1, mathContext).subtract(bd1).compareTo(BigDecimal.ZERO), 0);
  }

  public void testSqrtSimple() {
    _check(new BigDecimal("4767655423.1", new MathContext(2, RoundingMode.HALF_EVEN)), MathContext.DECIMAL32);
    _check(new BigDecimal("5423.1", new MathContext(2, RoundingMode.HALF_EVEN)), MathContext.DECIMAL32);
    _check(new BigDecimal("0.000001235423", new MathContext(2, RoundingMode.HALF_EVEN)), MathContext.DECIMAL32);
    _check(new BigDecimal("0.000000000000000000000000000000000001"), MathContext.DECIMAL128);
    _check(new BigDecimal("0"), MathContext.DECIMAL128);
    _check(BigDecimal.ZERO, MathContext.DECIMAL128);
    _check(new BigDecimal("1"), MathContext.DECIMAL128);
    _check(BigDecimal.ONE, MathContext.DECIMAL128);
  }

  public void testSqrtExact() {
    BigDecimal bd1 = new BigDecimal(BigInteger.valueOf(25 * 25));
    BigDecimal rt1 = BigDecimalMath.sqrt(bd1, MathContext.DECIMAL32);
    rt1.equals(RationalScalar.of(25, 1)); // gives false
    assertEquals(rt1.compareTo(new BigDecimal("25")), 0);
  }

  public void testSqrtTwo() {
    BigDecimal bd1 = new BigDecimal("2");
    BigDecimal rt1 = BigDecimalMath.sqrt(bd1, new MathContext(100, RoundingMode.HALF_EVEN));
    // mathematica N[Sqrt[2], 100] gives
    String m = "1.414213562373095048801688724209698078569671875376948073176679737990732478462107038850387534327641573";
    assertEquals(rt1.toString(), m);
  }

  public void testSqrtZero() {
    BigDecimal bd1 = new BigDecimal("0");
    BigDecimal rt1 = BigDecimalMath.sqrt(bd1, MathContext.DECIMAL64);
    assertEquals(rt1.compareTo(BigDecimal.ZERO), 0);
  }

  public void testSqrtNegative() {
    try {
      BigDecimalMath.sqrt(new BigDecimal("-2340"), MathContext.DECIMAL64);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testExpZero() {
    BigDecimal bd1 = BigDecimalMath.exp(BigDecimal.ZERO, MathContext.DECIMAL128);
    assertEquals(bd1, BigDecimal.ONE);
  }

  public void testExp() {
    // mathematica N[Exp[1], 34]
    String m = "2.718281828459045235360287471352662";
    BigDecimal bd1 = BigDecimalMath.exp(BigDecimal.ONE, MathContext.DECIMAL128);
    assertEquals(bd1.toString(), m);
  }

  public void testSin() {
    // mathematica: 0.90929742682568169539601986591174484270225497144789
    // ............ 0.9092974268256816953960198659117451
    Scalar s0 = Sin.of(RealScalar.of(2));
    BigDecimal bd1 = BigDecimalMath.sin(BigDecimal.valueOf(2), MathContext.DECIMAL128);
    Scalar s1 = DecimalScalar.of(bd1);
    assertTrue(Chop._13.close(s0, s1));
  }

  public void testSinh() {
    // mathematica: 0.90929742682568169539601986591174484270225497144789
    // ............ 0.9092974268256816953960198659117451
    Scalar s0 = Sinh.of(RealScalar.of(2));
    BigDecimal bd1 = BigDecimalMath.sinh(BigDecimal.valueOf(2), MathContext.DECIMAL128);
    Scalar s1 = DecimalScalar.of(bd1);
    assertTrue(Chop._13.close(s0, s1));
  }

  public void testCos() {
    Scalar s0 = Cos.of(RealScalar.of(2));
    BigDecimal bd1 = BigDecimalMath.cos(BigDecimal.valueOf(2), MathContext.DECIMAL128);
    Scalar s1 = DecimalScalar.of(bd1);
    assertTrue(Chop._13.close(s0, s1));
  }

  public void testCosh() {
    Scalar s0 = Cosh.of(RealScalar.of(2));
    BigDecimal bd1 = BigDecimalMath.cosh(BigDecimal.valueOf(2), MathContext.DECIMAL128);
    Scalar s1 = DecimalScalar.of(bd1);
    assertTrue(Chop._13.close(s0, s1));
  }
}
