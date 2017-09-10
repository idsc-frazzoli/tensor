// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import ch.ethz.idsc.tensor.red.Nest;
import ch.ethz.idsc.tensor.sca.Arg;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Cos;
import ch.ethz.idsc.tensor.sca.N;
import ch.ethz.idsc.tensor.sca.Power;
import ch.ethz.idsc.tensor.sca.Sin;
import ch.ethz.idsc.tensor.sca.Sqrt;
import junit.framework.TestCase;

public class DecimalScalar2Test extends TestCase {
  static final String PI100 = "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825342117068";

  public void testReciprocal() {
    Scalar s = DecimalScalar.of(new BigDecimal(PI100, MathContext.DECIMAL32));
    DecimalScalar r = (DecimalScalar) s.reciprocal();
    assertTrue(7 <= r.value().precision());
  }

  public void testN() {
    Scalar s = DecimalScalar.of(new BigDecimal(PI100, MathContext.DECIMAL32));
    assertEquals(N.DECIMAL64.of(s), s);
    assertTrue(N.DOUBLE.of(s) instanceof DoubleScalar);
  }

  public void testTrig() {
    Scalar s = DecimalScalar.of(new BigDecimal(PI100, MathContext.DECIMAL32));
    assertTrue(Chop._06.allZero(Sin.of(s)));
    assertTrue(Chop._06.close(Cos.of(s), RealScalar.ONE.negate()));
  }

  public void testPower() {
    Scalar scalar = DecimalScalar.of(new BigDecimal(PI100, MathContext.DECIMAL32));
    Scalar result = Power.of(scalar, 16);
    Scalar revers = Nest.of(Sqrt::of, result, 4);
    assertEquals(Scalars.compare(scalar, revers), 0);
  }

  public void testArg() {
    Scalar a = Arg.of(DecimalScalar.of(BigDecimal.ONE.negate()));
    Scalar b = DecimalScalar.of(new BigDecimal(PI100, MathContext.DECIMAL128));
    assertEquals(a, b);
  }

  private static void _checkEqCmp(Scalar s1, Scalar s2) {
    int cmp = Scalars.compare(s1, s2);
    boolean eq1 = s1.equals(s2);
    boolean eq2 = s2.equals(s1);
    assertEquals(eq1, eq2);
    assertEquals(cmp == 0, eq1);
  }

  public void testPairs() {
    Tensor vector = Tensors.of( //
        DoubleScalar.of(-0.0), //
        DoubleScalar.of(1.0 / 3.0), //
        DoubleScalar.of(2.0 / 3.0), //
        DoubleScalar.of(1.0), //
        RationalScalar.of(1, 3), //
        RationalScalar.of(2, 3), //
        DecimalScalar.of(new BigDecimal("0.33")), //
        DecimalScalar.of(BigDecimal.valueOf(1).divide(BigDecimal.valueOf(3), MathContext.DECIMAL32)), //
        DecimalScalar.of(BigDecimal.valueOf(2).divide(BigDecimal.valueOf(3), MathContext.DECIMAL32)), //
        DecimalScalar.of(BigDecimal.valueOf(1).divide(BigDecimal.valueOf(3), MathContext.DECIMAL64)), //
        DecimalScalar.of(BigDecimal.valueOf(2).divide(BigDecimal.valueOf(3), MathContext.DECIMAL64)), //
        DecimalScalar.of(BigDecimal.valueOf(1).divide(BigDecimal.valueOf(3), MathContext.DECIMAL128)), //
        DecimalScalar.of(BigDecimal.valueOf(2).divide(BigDecimal.valueOf(3), MathContext.DECIMAL128)), //
        DecimalScalar.of(BigDecimal.valueOf(1).divide(BigDecimal.valueOf(3), new MathContext(50, RoundingMode.HALF_EVEN))), //
        DecimalScalar.of(BigDecimal.valueOf(2).divide(BigDecimal.valueOf(3), new MathContext(50, RoundingMode.HALF_EVEN))), //
        DecimalScalar.of(BigDecimal.ONE), //
        DecimalScalar.of(BigDecimal.ZERO), //
        RealScalar.ONE, //
        RealScalar.ZERO //
    );
    for (int i = 0; i < vector.length(); ++i)
      for (int j = 0; j < vector.length(); ++j)
        _checkEqCmp(vector.Get(i), vector.Get(j));
  }

  public void testPrecision() {
    for (int value = 0; value < 10000; value += 31) {
      DecimalScalar ds = (DecimalScalar) DecimalScalar.of(Math.sqrt(value));
      String string = ds.toString();
      Scalar dbl_s = Scalars.fromString(string);
      assertEquals(ds, dbl_s);
    }
  }
}
