// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import junit.framework.TestCase;

public class UniformDistributionTest extends TestCase {
  public void testSimple() {
    PDF pdf = PDF.of(UniformDistribution.of(RealScalar.ONE, RealScalar.of(3)));
    assertEquals(pdf.p_lessThan(RealScalar.ONE), RealScalar.ZERO);
    assertEquals(pdf.p_lessThan(RealScalar.of(2)), RationalScalar.of(1, 2));
    assertEquals(pdf.p_lessThan(RealScalar.of(3)), RealScalar.ONE);
    assertEquals(pdf.p_lessThan(RealScalar.of(4)), RealScalar.ONE);
  }

  public void testFail() {
    try {
      UniformDistribution.of(RealScalar.ONE, RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      UniformDistribution.of(RealScalar.ONE, RealScalar.ZERO);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
