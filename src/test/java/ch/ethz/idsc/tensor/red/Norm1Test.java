// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import junit.framework.TestCase;

public class Norm1Test extends TestCase {
  public void testFail() {
    try {
      Norm._1.ofVector(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
