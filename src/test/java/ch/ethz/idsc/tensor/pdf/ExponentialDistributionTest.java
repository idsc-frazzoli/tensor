// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import junit.framework.TestCase;

public class ExponentialDistributionTest extends TestCase {
  public void testPositive() {
    PDF pdf = PDF.of(ExponentialDistribution.of(RealScalar.ONE));
    for (int c = 0; c < 100; ++c) {
      Scalar s = pdf.nextSample();
      assertTrue(Scalars.lessEquals(RealScalar.ZERO, s));
    }
  }

  public void testMean() {
    PDF pdf = PDF.of(ExponentialDistribution.of(RealScalar.of(2)));
    // TODO check that mean
    for (int c = 0; c < 100; ++c) {
      Scalar s = pdf.nextSample();
      assertTrue(Scalars.lessEquals(RealScalar.ZERO, s));
    }
  }

  public void testFailL() {
    try {
      ExponentialDistribution.of(RealScalar.ZERO);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      ExponentialDistribution.of(RealScalar.of(-.1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNextUp() {
    double zero = 0;
    double nonzero = Math.nextUp(zero);
    double log = Math.log(nonzero);
    assertTrue(-2000 < log);
  }
}
