// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
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
    UniformDistribution distribution = (UniformDistribution) UniformDistribution.unit();
    assertEquals(distribution.mean(), RationalScalar.of(1, 2));
    assertEquals(distribution.variance(), RationalScalar.of(1, 12));
  }

  public void testRandomVariate() {
    Scalar s1 = RandomVariate.of(UniformDistribution.of(0, 1), new Random(1000));
    Scalar s2 = RandomVariate.of(UniformDistribution.unit(), new Random(1000));
    assertEquals(s1, s2);
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
