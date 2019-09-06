// code by jph
package ch.ethz.idsc.tensor.sca;

import java.math.BigDecimal;
import java.math.MathContext;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DecimalScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.num.GaussScalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class CoshTest extends TestCase {
  public void testReal() {
    Scalar c = Cosh.FUNCTION.apply(RealScalar.of(2));
    Scalar s = DoubleScalar.of(Math.cosh(2));
    Scalar t = Cosh.of(RealScalar.of(2));
    assertEquals(c, s);
    assertEquals(c, t);
  }

  public void testComplex() {
    Scalar c = Cosh.of(ComplexScalar.of(2, 3.));
    // -3.72455 + 0.511823 I
    Scalar s = Scalars.fromString("-3.7245455049153224+0.5118225699873846*I");
    assertEquals(c, s);
  }

  public void testDecimal() {
    Scalar scalar = Cosh.of(DecimalScalar.of(new BigDecimal("1.2356", MathContext.DECIMAL128)));
    assertTrue(scalar instanceof DecimalScalar);
    assertEquals(scalar, DoubleScalar.of(Math.cosh(1.2356)));
  }

  public void testQuantityFail() {
    try {
      Cosh.of(Quantity.of(1, "deg"));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testGaussScalarFail() {
    try {
      Cosh.of(GaussScalar.of(6, 7));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
