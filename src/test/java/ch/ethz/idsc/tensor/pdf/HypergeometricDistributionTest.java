// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.red.Mean;
import ch.ethz.idsc.tensor.red.Variance;
import junit.framework.TestCase;

public class HypergeometricDistributionTest extends TestCase {
  public void testPdf() {
    PDF pdf = PDF.of(HypergeometricDistribution.of(10, 50, 100));
    Scalar sum = RealScalar.ZERO;
    for (int c = 0; c <= 10; ++c)
      sum = sum.add(pdf.at(RealScalar.of(c)));
    assertEquals(sum, RealScalar.ONE);
  }

  public void testFail() {
    // int N, int n, int m_n
    // 0 < N && N <= m_n && 0 <= n && n <= m_n
    try {
      HypergeometricDistribution.of(0, 50, 100); // violates 0 < N
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      HypergeometricDistribution.of(5, -1, 100); // violates 0 <= n
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      HypergeometricDistribution.of(11, 10, 10); // violates N <= m_n
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      HypergeometricDistribution.of(10, 11, 10); // violates n <= m_n
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

  public void testInverseCDF1() {
    Distribution distribution = HypergeometricDistribution.of(10, 0, 100);
    InverseCDF inverseCDF = InverseCDF.of(distribution);
    Scalar r = inverseCDF.quantile(RealScalar.ONE);
    assertEquals(r, RealScalar.ZERO);
  }

  public void testInverseCDF2() {
    Distribution distribution = HypergeometricDistribution.of(10, 5, 100);
    InverseCDF inverseCDF = InverseCDF.of(distribution);
    assertEquals(inverseCDF.quantile(RealScalar.ONE), RealScalar.of(5)); // confirmed with Mathematica
  }

  public void testInverseCDF3() {
    Distribution distribution = HypergeometricDistribution.of(6, 10, 100);
    InverseCDF inverseCDF = InverseCDF.of(distribution);
    assertEquals(inverseCDF.quantile(RealScalar.ONE), RealScalar.of(6)); // confirmed with Mathematica
  }

  public void testOutside() {
    PDF pdf = PDF.of(HypergeometricDistribution.of(10, 50, 100));
    assertEquals(pdf.at(RealScalar.of(-1)), RealScalar.ZERO);
    assertEquals(pdf.at(RealScalar.of(11)), RealScalar.ZERO);
  }

  public void testMean() {
    Scalar mean = Mean.of(HypergeometricDistribution.of(10, 50, 100));
    assertEquals(mean, RealScalar.of(5));
  }

  public void testVariance() {
    Scalar variance = Variance.of(HypergeometricDistribution.of(10, 50, 100));
    assertEquals(variance, Scalars.fromString("25/11"));
  }

  public void testToString() {
    Distribution distribution = HypergeometricDistribution.of(10, 50, 100);
    assertEquals(distribution.toString(), "HypergeometricDistribution[10, 50, 100]");
  }
}
