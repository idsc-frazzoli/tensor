// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import junit.framework.TestCase;

public class NormInfinityTest extends TestCase {
  public void testFail() {
    try {
      Norm.INFINITY.ofVector(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
