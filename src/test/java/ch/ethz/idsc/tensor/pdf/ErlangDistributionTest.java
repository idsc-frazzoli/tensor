// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class ErlangDistributionTest extends TestCase {
  public void testSimple() {
    Distribution distribution = ErlangDistribution.of(3, RealScalar.of(1.8));
    PDF pdf = PDF.of(distribution);
    Scalar p = pdf.at(RealScalar.of(3.2));
    assertTrue(Chop._06.close(p, RealScalar.of(0.0940917)));
    assertEquals(pdf.at(RealScalar.of(0)), RealScalar.ZERO);
    assertEquals(pdf.at(RealScalar.of(-0.12)), RealScalar.ZERO);
  }

  public void testFail() {
    try {
      ErlangDistribution.of(0, RealScalar.of(1.8));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
