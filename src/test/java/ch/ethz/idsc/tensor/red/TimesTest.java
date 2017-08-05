// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import junit.framework.TestCase;

public class TimesTest extends TestCase {
  public void testSimple() {
    assertEquals(Times.of(RealScalar.of(3), RealScalar.of(8)), RealScalar.of(3 * 8));
  }

  public void testEmpty() {
    assertEquals(Times.of(), RealScalar.ONE);
  }
}
