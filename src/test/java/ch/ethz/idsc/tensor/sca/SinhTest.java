// code by jph
package ch.ethz.idsc.tensor.sca;

import java.math.BigDecimal;
import java.math.MathContext;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DecimalScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.GaussScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import junit.framework.TestCase;

public class SinhTest extends TestCase {
  public void testReal() {
    Scalar i = RealScalar.of(2);
    Scalar c = Sinh.FUNCTION.apply(i);
    Scalar s = DoubleScalar.of(Math.sinh(2));
    assertEquals(c, Sinh.of(i));
    assertEquals(c, s);
  }

  public void testComplex() {
    Scalar c = Sinh.of(ComplexScalar.of(2, 3.));
    // -3.59056 + 0.530921 I
    Scalar s = Scalars.fromString("-3.59056458998578+0.5309210862485197*I");
    assertEquals(c, s);
  }

  public void testDecimal() {
    Scalar c = Sinh.of(DecimalScalar.of(new BigDecimal("1.2356", MathContext.DECIMAL128)));
    assertTrue(c instanceof DecimalScalar);
    assertTrue(Chop._11.close(c, DoubleScalar.of(Math.sinh(1.2356))));
  }

  public void testFail() {
    try {
      Sinh.of(GaussScalar.of(6, 7));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
