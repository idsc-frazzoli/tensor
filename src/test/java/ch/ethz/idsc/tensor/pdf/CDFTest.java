// code by jph
package ch.ethz.idsc.tensor.pdf;

import junit.framework.TestCase;

public class CDFTest extends TestCase {
  public void testFail() {
    try {
      CDF.of(null);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
