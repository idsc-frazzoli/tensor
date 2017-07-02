// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import junit.framework.TestCase;

public class RampTest extends TestCase {
  public void testSimple() {
    assertEquals(Ramp.FUNCTION.apply(RealScalar.ZERO), RealScalar.ZERO);
    assertEquals(Ramp.FUNCTION.apply(RealScalar.of(-6)), RealScalar.ZERO);
    assertEquals(Ramp.FUNCTION.apply(RealScalar.of(26)), RealScalar.of(26));
  }
}
