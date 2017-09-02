// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigDecimal;
import java.math.MathContext;

import ch.ethz.idsc.tensor.red.Nest;
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
    assertEquals(N.of(s, MathContext.DECIMAL64), s);
    assertTrue(N.of(s) instanceof DoubleScalar);
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
}
