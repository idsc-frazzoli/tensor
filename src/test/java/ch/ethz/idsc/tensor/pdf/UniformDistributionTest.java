// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import junit.framework.TestCase;

public class UniformDistributionTest extends TestCase {
  public void testSimple() {
    CDF cdf = CDF.of(UniformDistribution.of(RealScalar.ONE, RealScalar.of(3)));
    assertEquals(cdf.p_lessThan(RealScalar.ONE), RealScalar.ZERO);
    assertEquals(cdf.p_lessThan(RealScalar.of(2)), RationalScalar.of(1, 2));
    assertEquals(cdf.p_lessThan(RealScalar.of(3)), RealScalar.ONE);
    assertEquals(cdf.p_lessThan(RealScalar.of(4)), RealScalar.ONE);
  }

  public void testUnit() {
    UniformDistribution distribution = (UniformDistribution) UniformDistribution.of();
    assertEquals(distribution.mean(), RationalScalar.of(1, 2));
    assertEquals(distribution.variance(), RationalScalar.of(1, 12));
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
