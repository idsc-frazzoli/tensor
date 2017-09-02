// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigDecimal;
import java.math.MathContext;

import junit.framework.TestCase;

public class DecimalScalar2Test extends TestCase {
  static final String PI100 = "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825342117068";

  public void testRationalScalar() {
    RationalScalar s = (RationalScalar) RationalScalar.of(127, 435);
    BigDecimal bd1 = new BigDecimal(s.numerator());
    // System.out.println(s);
    // System.out.println(bd1.precision());
  }

  public void testReciprocal() {
    Scalar s = DecimalScalar.of(new BigDecimal(PI100, MathContext.DECIMAL32));
    DecimalScalar r = (DecimalScalar) s.reciprocal();
    // System.out.println(r);
    // System.out.println(r.precision());
    assertTrue(7 <= r.precision());
    // System.out.println(s.multiply(r));
  }
}
