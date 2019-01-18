// code by jph
package ch.ethz.idsc.tensor;

import junit.framework.TestCase;

public class ScalarBinaryPowerTest extends TestCase {
  public void testSimple() {
    try {
      Scalars.binaryPower(null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
