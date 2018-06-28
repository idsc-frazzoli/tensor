// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class StandardNormalDistributionTest extends TestCase {
  public void testZero() {
    Scalar x = StandardNormalDistribution.INSTANCE.at(RealScalar.ZERO);
    assertTrue(x.toString().startsWith("0.398942280"));
  }

  public void testOneSymmetric() {
    Scalar x = StandardNormalDistribution.INSTANCE.at(RealScalar.ONE);
    Scalar xn = StandardNormalDistribution.INSTANCE.at(RealScalar.ONE.negate());
    assertTrue(x.toString().startsWith("0.241970724"));
    assertEquals(x, xn);
  }

  public void testCdf() {
    CDF cdf = StandardNormalDistribution.INSTANCE;
    {
      Scalar p = cdf.p_lessEquals(RealScalar.ZERO);
      assertTrue(Chop._07.close(p, RealScalar.of(0.5)));
    }
    {
      Scalar p = cdf.p_lessThan(RealScalar.of(.3));
      assertTrue(p.toString().startsWith("0.617911"));
      Scalar q = cdf.p_lessThan(RealScalar.of(-.3));
      assertEquals(p.add(q), RealScalar.ONE);
    }
  }

  public void testInverseCDF() {
    Distribution distribution = StandardNormalDistribution.INSTANCE;
    InverseCDF inverseCDF = InverseCDF.of(distribution);
    try {
      inverseCDF.quantile(RealScalar.of(1.1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      inverseCDF.quantile(RealScalar.of(-0.1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
