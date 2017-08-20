// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.StringScalar;
import junit.framework.TestCase;

public class ImagTest extends TestCase {
  public void testFail() {
    try {
      Imag.of(StringScalar.of("string"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
