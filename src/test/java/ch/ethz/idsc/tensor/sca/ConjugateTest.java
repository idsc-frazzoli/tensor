// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.io.StringScalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class ConjugateTest extends TestCase {
  public void testQuantity1() {
    Scalar s = Scalars.fromString("0+0*I[m*s]");
    assertTrue(s instanceof Quantity);
    assertEquals(Real.of(s).toString(), "0[m*s]");
    assertEquals(Imag.of(s).toString(), "0[m*s]");
    assertEquals(Conjugate.of(s).toString(), "0[m*s]");
  }

  public void testQuantity2() {
    Scalar s = Scalars.fromString("3+5*I[m*s]");
    assertTrue(s instanceof Quantity);
    assertEquals(Real.of(s), Quantity.of(3, "m*s"));
    assertEquals(Imag.of(s), Quantity.of(5, "m*s"));
    assertEquals(Conjugate.of(s), Scalars.fromString("3-5*I[m*s]"));
  }

  public void testFail() {
    try {
      Conjugate.of(StringScalar.of("asd"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
