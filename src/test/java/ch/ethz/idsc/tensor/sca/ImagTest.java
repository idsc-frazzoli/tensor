// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.io.StringScalar;
import junit.framework.TestCase;

public class ImagTest extends TestCase {
  public void testExact() {
    Scalar scalar = Imag.FUNCTION.apply(Scalars.fromString("3+I*6/7"));
    assertEquals(scalar, RationalScalar.of(6, 7));
    assertTrue(ExactScalarQ.of(scalar));
  }

  public void testFail() {
    try {
      Imag.of(StringScalar.of("string"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
