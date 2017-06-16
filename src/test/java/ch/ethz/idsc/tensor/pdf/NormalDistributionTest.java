// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class NormalDistributionTest extends TestCase {
  public void testSimple() {
    Scalar mean = RationalScalar.of(3, 5);
    Distribution distribution = NormalDistribution.of(mean, RationalScalar.of(4, 9));
    assertEquals(distribution.mean(), mean);
  }

  public void testCdf() {
    Scalar mean = RationalScalar.of(3, 5);
    Distribution distribution = NormalDistribution.of(mean, RationalScalar.of(4, 9));
    CDF cdf = CDF.of(distribution);
    assertEquals(cdf.p_lessThan(mean), RationalScalar.of(1, 2));
    assertEquals(cdf.p_lessEquals(mean), RationalScalar.of(1, 2));
  }
}
