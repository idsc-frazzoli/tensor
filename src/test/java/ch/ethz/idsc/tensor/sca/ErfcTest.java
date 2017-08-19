// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.GaussScalar;
import junit.framework.TestCase;

public class ErfcTest extends TestCase {
  public void testFail() {
    try {
      Erfc.FUNCTION.apply(GaussScalar.of(6, 7));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
