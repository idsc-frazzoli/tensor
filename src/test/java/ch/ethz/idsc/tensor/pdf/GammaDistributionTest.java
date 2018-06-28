// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class GammaDistributionTest extends TestCase {
  public void testPdf() {
    Distribution distribution = GammaDistribution.of(RealScalar.of(1.123), RealScalar.of(2.3));
    PDF pdf = PDF.of(distribution);
    {
      Scalar prob = pdf.at(RealScalar.of(0.78));
      assertTrue(Chop._08.close(prob, DoubleScalar.of(0.28770929331586703)));
    }
    {
      assertTrue(Scalars.isZero(pdf.at(RealScalar.of(-.3))));
    }
  }

  public void testExp() {
    Distribution distribution = GammaDistribution.of(RealScalar.of(1.0), RealScalar.of(2.3));
    assertTrue(distribution instanceof ExponentialDistribution);
  }

  public void testMean() {
    Scalar a = RealScalar.of(1.123);
    Scalar b = RealScalar.of(2.3);
    Distribution distribution = GammaDistribution.of(a, b);
    assertEquals(Expectation.mean(distribution), a.multiply(b));
    assertEquals(Expectation.variance(distribution), a.multiply(b).multiply(b));
  }

  public void testToString() {
    Scalar a = RealScalar.of(1.123);
    Scalar b = RealScalar.of(2.3);
    Distribution distribution = GammaDistribution.of(a, b);
    // Distribution distribution = GeometricDistribution.of(RationalScalar.of(1, 3));
    assertEquals(distribution.toString(), "GammaDistribution[1.123, 2.3]");
  }

  public void testFail() {
    try {
      GammaDistribution.of(RealScalar.of(-1.0), RealScalar.of(2.3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      GammaDistribution.of(RealScalar.of(0.1), RealScalar.of(-2.3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
