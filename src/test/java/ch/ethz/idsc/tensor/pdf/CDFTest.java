// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RealScalar;
import junit.framework.TestCase;

public class CDFTest extends TestCase {
  public void testCDFFail() {
    Distribution distribution = ErlangDistribution.of(3, RealScalar.of(.3));
    try {
      CDF.of(distribution);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNullFail() {
    try {
      CDF.of(null);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
