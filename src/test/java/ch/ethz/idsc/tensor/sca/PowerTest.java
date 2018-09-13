// code by jph
package ch.ethz.idsc.tensor.sca;

import java.math.BigDecimal;
import java.math.MathContext;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DecimalScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.GaussScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.io.StringScalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class PowerTest extends TestCase {
  public void testInteger() {
    assertEquals(Power.of(2, 4), RealScalar.of(16));
    assertEquals(Power.of(-4, 5), RealScalar.of(-1024));
  }

  public void testExponentZero() {
    assertEquals(Power.of(+2, 0), RealScalar.ONE);
    assertEquals(Power.of(+1, 0), RealScalar.ONE);
    assertEquals(Power.of(+0, 0), RealScalar.ONE);
    assertEquals(Power.of(-1, 0), RealScalar.ONE);
    assertEquals(Power.of(-2, 0), RealScalar.ONE);
  }

  public void testNumberScalar() {
    Scalar scalar = Power.of(2, RationalScalar.of(2, 3));
    assertTrue(Chop._13.close(scalar, Scalars.fromString("1.5874010519681994`")));
  }

  public void testSqrt() {
    assertEquals(Power.of(2, .5), Sqrt.of(RealScalar.of(2)));
    assertEquals(Power.of(14, .5), Sqrt.of(RealScalar.of(14)));
  }

  public void testZero() {
    assertEquals(Power.of(0, +0), RealScalar.ONE);
    assertEquals(Power.of(0, +1), RealScalar.ZERO);
    assertEquals(Power.of(0, +2), RealScalar.ZERO);
  }

  public void testZeroFail() {
    try {
      Power.of(0, -2);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testZeroComplex() {
    assertEquals(Power.of(RealScalar.ZERO, Scalars.fromString("0.1+3*I")), RealScalar.ZERO);
    assertEquals(Power.of(RealScalar.ZERO, Scalars.fromString("0.1-3*I/2")), RealScalar.ZERO);
  }

  public void testZeroComplexFail() {
    try {
      Power.of(RealScalar.ZERO, ComplexScalar.I);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Power.of(RealScalar.ZERO, Scalars.fromString("-0.1+3*I"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNegative() {
    assertEquals(Power.of(2, -4), RealScalar.of(16).reciprocal());
    assertEquals(Power.of(-4, -5), RealScalar.of(-1024).reciprocal());
  }

  public void testNegativeFractional() {
    Scalar result = Power.of(-2.2, 1.3);
    Scalar gndtru = Scalars.fromString("-1.6382047104755275 - 2.254795345529229* I");
    assertEquals(result, gndtru);
  }

  public void testNegativeFractionalNeg() {
    Scalar result = Power.of(-2.2, -1.3);
    Scalar gndtru = Scalars.fromString("-0.21089641642663778` + 0.290274014661784` *I ");
    assertEquals(result, gndtru);
  }

  public void testComplex() {
    Scalar a = ComplexScalar.of(2, +3);
    Scalar b = ComplexScalar.of(4, -2);
    Scalar c = Power.of(a, b);
    // Mathematica: 245.099 + 1181.35 I
    assertEquals(c, Scalars.fromString("245.09854196562927+1181.3509801973048*I"));
  }

  public void testFunction() {
    assertEquals(RealScalar.of(7).map(Power.function(.5)), Sqrt.of(RealScalar.of(7)));
    assertEquals(Power.function(.5).apply(RealScalar.of(7)), Sqrt.of(RealScalar.of(7)));
  }

  public void testTypeFail() {
    Scalar scalar = StringScalar.of("some");
    try {
      Power.of(scalar, 0);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testDecimal() {
    Scalar d1 = DecimalScalar.of(new BigDecimal("1.234", MathContext.DECIMAL128));
    assertEquals(Power.of(d1, 2.34), DoubleScalar.of(Math.pow(1.234, 2.34)));
  }

  public void testGaussScalar() {
    Scalar scalar = GaussScalar.of(6, 31);
    try {
      Power.of(scalar, 3.13);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testQuantity1() {
    Scalar qs1 = Quantity.of(9, "m^2");
    Scalar res = Power.of(qs1, RealScalar.of(3));
    Scalar act = Quantity.of(729, "m^6");
    assertEquals(res, act);
  }

  public void testQuantity2() {
    Scalar qs1 = Quantity.of(-2, "m^-3*rad");
    Scalar res = Power.of(qs1, RealScalar.of(3));
    Scalar act = Quantity.of(-8, "m^-9*rad^3");
    assertEquals(res, act);
  }

  public void testQuantityFail() {
    Scalar qs1 = Quantity.of(2, "cd");
    Scalar qs2 = Quantity.of(4, "cd");
    try {
      Power.of(qs1, qs2);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
