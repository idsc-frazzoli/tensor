// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.red.Mean;
import ch.ethz.idsc.tensor.red.Norm;
import junit.framework.TestCase;

public class ExponentialDistributionTest extends TestCase {
  public void testPositive() {
    Distribution distribution = ExponentialDistribution.of(RealScalar.ONE);
    // PDF pdf = PDF.of(ExponentialDistribution.of(RealScalar.ONE));
    for (int c = 0; c < 100; ++c) {
      Scalar s = RandomVariate.of(distribution);
      assertTrue(Scalars.lessEquals(RealScalar.ZERO, s));
    }
  }

  public void testCDF() {
    Distribution distribution = ExponentialDistribution.of(RealScalar.ONE);
    CDF cdf = CDF.of(distribution);
    assertEquals(cdf.p_lessThan(RealScalar.of(-1)), RealScalar.ZERO);
    assertEquals(cdf.p_lessEquals(RealScalar.of(-1)), RealScalar.ZERO);
    assertEquals(cdf.p_lessThan(RealScalar.of(0)), RealScalar.ZERO);
    assertEquals(cdf.p_lessEquals(RealScalar.of(0)), RealScalar.ZERO);
  }

  public void testMean() {
    Scalar lambda = RealScalar.of(2);
    Distribution distribution = ExponentialDistribution.of(lambda);
    // PDF pdf = PDF.of(distribution);
    Tensor all = Tensors.empty();
    for (int c = 0; c < 2000; ++c) {
      Scalar s = RandomVariate.of(distribution);
      all.append(s);
    }
    Scalar mean = lambda.invert();
    assertEquals(Expectation.mean(distribution), mean);
    Scalar diff = Norm._2.of(Mean.of(all).Get().subtract(mean));
    assertTrue(Scalars.lessThan(diff, RealScalar.of(0.05)));
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
