// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class HypergeometricDistributionTest extends TestCase {
  public void testSimple() {
    PDF pdf = PDF.of(HypergeometricDistribution.of(10, 50, 100));
    Scalar sum = RealScalar.ZERO;
    for (int c = 0; c <= 10; ++c)
      sum = sum.add(pdf.at(RealScalar.of(c)));
    assertEquals(sum, RealScalar.ONE);
  }

  public void testFail() {
    try {
      HypergeometricDistribution.of(0, 50, 100);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
