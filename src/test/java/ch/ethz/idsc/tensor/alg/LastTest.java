// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class LastTest extends TestCase {
  public void testLast() {
    assertEquals(Last.of(Tensors.vector(3, 2, 6, 4)), RealScalar.of(4));
  }

  public void testFailEmpty() {
    try {
      Last.of(Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailScalar() {
    try {
      Last.of(RealScalar.of(99));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
