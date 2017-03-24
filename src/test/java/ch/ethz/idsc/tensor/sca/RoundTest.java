// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.DoubleScalar;
import junit.framework.TestCase;

public class RoundTest extends TestCase {
  public void testDouble() {
    assertEquals(Round.function.apply(DoubleScalar.of(11.3)), DoubleScalar.of(11));
    assertEquals(Round.function.apply(DoubleScalar.of(11.5)), DoubleScalar.of(12));
  }
}
