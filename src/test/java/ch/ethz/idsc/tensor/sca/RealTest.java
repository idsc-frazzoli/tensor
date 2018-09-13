// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.io.StringScalar;
import junit.framework.TestCase;

public class RealTest extends TestCase {
  public void testReal() {
    Scalar scalar = Scalars.fromString("11");
    assertEquals(Real.FUNCTION.apply(scalar), RealScalar.of(11));
    assertEquals(Imag.FUNCTION.apply(scalar), RealScalar.of(0));
    assertEquals(Real.of(scalar), RealScalar.of(11));
    assertEquals(Imag.of(scalar), RealScalar.of(0));
  }

  public void testComplex() {
    Scalar scalar = Scalars.fromString("11+3.5*I");
    assertEquals(Real.FUNCTION.apply(scalar), RealScalar.of(11));
    assertEquals(Imag.FUNCTION.apply(scalar), RealScalar.of(3.5));
    assertEquals(Real.of(scalar), RealScalar.of(11));
    assertEquals(Imag.of(scalar), RealScalar.of(3.5));
  }

  public void testFail() {
    Scalar scalar = StringScalar.of("string");
    try {
      Real.of(scalar);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
