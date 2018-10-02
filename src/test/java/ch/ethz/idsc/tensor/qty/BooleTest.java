// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.RealScalar;
import junit.framework.TestCase;

public class BooleTest extends TestCase {
  public void testSimple() {
    assertEquals(Boole.of(true), RealScalar.ONE);
    assertEquals(Boole.of(false), RealScalar.ZERO);
  }
}
