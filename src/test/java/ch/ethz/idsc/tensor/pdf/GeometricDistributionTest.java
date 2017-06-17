// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.ExactNumberQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class GeometricDistributionTest extends TestCase {
  public void testSimple() {
    PDF pdf = PDF.of(GeometricDistribution.of(RationalScalar.of(1, 3)));
    assertEquals(pdf.at(RealScalar.ZERO), RationalScalar.of(1, 3));
    assertEquals(pdf.at(RealScalar.of(1)), RationalScalar.of(2, 9));
    assertEquals(pdf.at(RealScalar.of(2)), RationalScalar.of(4, 27));
    assertEquals(pdf.at(RealScalar.of(1)), RationalScalar.of(1, 3).multiply(RationalScalar.of(2, 3)));
    assertEquals(pdf.at(RealScalar.of(2)), RationalScalar.of(1, 3).multiply(RationalScalar.of(4, 9)));
  }

  public void testNarrow() {
    final Scalar p = RationalScalar.of(1, 19);
    GeometricDistribution distribution = (GeometricDistribution) GeometricDistribution.of(p);
    PDF pdf = PDF.of(distribution);
    Scalar peq0 = pdf.at(RealScalar.ZERO);
    Scalar peq1 = pdf.at(RealScalar.ONE);
    Scalar plt2 = peq0.add(peq1);
    assertEquals(peq0, p);
    CDF cdf = CDF.of(distribution);
    assertEquals(cdf.p_lessThan(RealScalar.ZERO), RealScalar.ZERO);
    assertEquals(cdf.p_lessEquals(RealScalar.ZERO), p);
    assertEquals(cdf.p_lessThan(RealScalar.of(.1)), p);
    assertEquals(cdf.p_lessEquals(RealScalar.of(.1)), p);
    assertEquals(cdf.p_lessThan(RealScalar.ONE), p);
    assertEquals(cdf.p_lessThan(RealScalar.of(1.1)), plt2);
    assertEquals(cdf.p_lessEquals(RealScalar.ONE), plt2);
    assertEquals(cdf.p_lessEquals(RealScalar.of(1.1)), plt2);
    Scalar large = cdf.p_lessEquals(RealScalar.of(100.1));
    assertTrue(ExactNumberQ.of(large));
  }

  public void testFailP() {
    try {
      GeometricDistribution.of(RealScalar.ZERO);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      GeometricDistribution.of(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNumerics() {
    Distribution distribution = GeometricDistribution.of(RealScalar.of(.002));
    CDF cdf = CDF.of(distribution);
    Scalar s = cdf.p_lessEquals(RealScalar.of(1000000000));
    assertEquals(s, RealScalar.ONE);
  }

  public void testOutside() {
    PDF pdf = PDF.of(GeometricDistribution.of(RationalScalar.of(1, 3)));
    assertEquals(pdf.at(RealScalar.of(-1)), RealScalar.ZERO);
  }
}
