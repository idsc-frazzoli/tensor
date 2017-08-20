// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import junit.framework.TestCase;

public class RampTest extends TestCase {
  public void testSimple() {
    assertEquals(Ramp.of(RealScalar.ZERO), RealScalar.ZERO);
    assertEquals(Ramp.FUNCTION.apply(RealScalar.of(-6)), RealScalar.ZERO);
    assertEquals(Ramp.of(RealScalar.of(26)), RealScalar.of(26));
  }

  public void testInfty() {
    assertEquals(Ramp.of(DoubleScalar.POSITIVE_INFINITY), DoubleScalar.POSITIVE_INFINITY);
  }
}
