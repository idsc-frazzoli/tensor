// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import junit.framework.TestCase;

public class TimesTest extends TestCase {
  public void testProduct() {
    assertEquals(Times.of(RealScalar.of(3), RealScalar.of(8)), RealScalar.of(3 * 8));
    assertEquals(Times.of(RealScalar.of(3), RealScalar.of(8), RealScalar.of(-4)), RealScalar.of(3 * 8 * -4));
  }

  public void testEmpty() {
    assertEquals(Times.of(), RealScalar.ONE);
  }
}
