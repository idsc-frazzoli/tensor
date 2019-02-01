// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.RealScalar;
import junit.framework.TestCase;

public class BooleTest extends TestCase {
  public void testTrue() {
    assertEquals(Boole.of(true), RealScalar.ONE);
  }

  public void testFalse() {
    assertEquals(Boole.of(false), RealScalar.ZERO);
  }
}
