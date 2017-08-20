// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.GaussScalar;
import junit.framework.TestCase;

public class ConjugateTest extends TestCase {
  public void testFail() {
    try {
      Conjugate.of(GaussScalar.of(1, 7));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
