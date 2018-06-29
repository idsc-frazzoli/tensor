// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.Gaussian;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class RampTest extends TestCase {
  public void testRealScalar() {
    assertEquals(Ramp.of(RealScalar.ZERO), RealScalar.ZERO);
    assertEquals(Ramp.FUNCTION.apply(RealScalar.of(-6)), RealScalar.ZERO);
    assertEquals(Ramp.of(RealScalar.of(26)), RealScalar.of(26));
  }

  public void testInfty() {
    assertEquals(Ramp.of(DoubleScalar.POSITIVE_INFINITY), DoubleScalar.POSITIVE_INFINITY);
  }

  public void testQuantity() {
    Scalar qs1 = Quantity.of(1, "m");
    Scalar qs2 = Quantity.of(-2, "m");
    assertEquals(Ramp.of(qs1), qs1);
    assertEquals(Ramp.of(qs2), qs1.zero());
  }

  public void testFail() {
    Scalar scalar = Gaussian.of(2, 3);
    try {
      Ramp.FUNCTION.apply(scalar);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
