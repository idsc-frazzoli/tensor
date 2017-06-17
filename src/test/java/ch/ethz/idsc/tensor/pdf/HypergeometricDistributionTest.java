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
    try {
      HypergeometricDistribution.of(5, -1, 100);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testSpecialCase() {
    PDF pdf = PDF.of(HypergeometricDistribution.of(10, 0, 100));
    assertEquals(pdf.at(RealScalar.of(-1)), RealScalar.ZERO);
    assertEquals(pdf.at(RealScalar.of(0)), RealScalar.ONE);
    assertEquals(pdf.at(RealScalar.of(1)), RealScalar.ZERO);
    assertEquals(pdf.at(RealScalar.of(10)), RealScalar.ZERO);
  }

  public void testOutside() {
    PDF pdf = PDF.of(HypergeometricDistribution.of(10, 50, 100));
    assertEquals(pdf.at(RealScalar.of(-1)), RealScalar.ZERO);
    assertEquals(pdf.at(RealScalar.of(11)), RealScalar.ZERO);
  }
}
