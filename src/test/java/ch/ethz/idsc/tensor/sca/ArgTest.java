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
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class ArgTest extends TestCase {
  public void testArg() {
    Scalar scalar = ComplexScalar.of(RationalScalar.of(-2, 3), RationalScalar.of(-5, 100));
    assertEquals(Arg.of(scalar).toString(), "-3.066732805879026");
    assertEquals(Arg.of(RealScalar.ZERO), RealScalar.ZERO);
    assertEquals(Arg.of(DoubleScalar.of(.2)), RealScalar.ZERO);
    assertEquals(Arg.of(DoubleScalar.of(-1)), DoubleScalar.of(Math.PI));
    assertEquals(Arg.of(RationalScalar.of(-1, 3)), DoubleScalar.of(Math.PI));
  }

  public void testDecimal() {
    assertEquals(Arg.of(DecimalScalar.of(new BigDecimal("3.14", MathContext.DECIMAL128))), RealScalar.ZERO);
    assertEquals(Arg.of(DecimalScalar.of(new BigDecimal("-112.14", MathContext.DECIMAL128))), RealScalar.of(Math.PI));
  }

  public void testQuantity() {
    Scalar s = Quantity.of(ComplexScalar.of(3, 4), "s*m^3");
    Scalar a = Arg.of(s);
    Scalar b = ArcTan.of(RealScalar.of(3), RealScalar.of(4));
    assertEquals(a, b);
  }

  public void testFail() {
    Scalar scalar = GaussScalar.of(1, 7);
    try {
      Arg.of(scalar);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
