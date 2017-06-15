// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import junit.framework.TestCase;

public class DiscreteCDFTest extends TestCase {
  public void testExactLessEquals() {
    Distribution distribution = BinomialDistribution.of(21, RationalScalar.of(7, 13));
    DiscreteDistribution discreteDistribution = (DiscreteDistribution) distribution;
    CDF cdf = CDF.of(distribution);
    DiscreteCDF discreteCDF = (DiscreteCDF) cdf;
    assertEquals(cdf.p_lessEquals(RealScalar.of(-10)), RealScalar.ZERO);
    assertEquals(cdf.p_lessEquals(RealScalar.of(0)), discreteDistribution.p_equals(0));
    assertEquals(cdf.p_lessEquals(RealScalar.of(1)), //
        discreteDistribution.p_equals(0).add(discreteDistribution.p_equals(1)) //
    );
    assertFalse(discreteCDF.cdf_finished());
    assertEquals(cdf.p_lessEquals(RealScalar.of(1000000000)), RealScalar.ONE);
    assertTrue(discreteCDF.cdf_finished());
  }

  public void testExactLessThan() {
    Distribution distribution = BinomialDistribution.of(21, RationalScalar.of(7, 13));
    DiscreteDistribution discreteDistribution = (DiscreteDistribution) distribution;
    CDF cdf = CDF.of(distribution);
    DiscreteCDF discreteCDF = (DiscreteCDF) cdf;
    assertEquals(cdf.p_lessThan(RealScalar.of(-10)), RealScalar.ZERO);
    assertEquals(cdf.p_lessThan(RealScalar.of(0)), RealScalar.ZERO);
    assertEquals(cdf.p_lessThan(RealScalar.of(1e-8)), discreteDistribution.p_equals(0));
    assertEquals(cdf.p_lessThan(RealScalar.of(1)), discreteDistribution.p_equals(0));
    assertEquals(cdf.p_lessThan(RealScalar.of(2)), //
        discreteDistribution.p_equals(0).add(discreteDistribution.p_equals(1)) //
    );
    assertFalse(discreteCDF.cdf_finished());
    assertEquals(cdf.p_lessThan(RealScalar.of(1000000000)), RealScalar.ONE);
    assertTrue(discreteCDF.cdf_finished());
  }

  private static void _checkDiscreteCDFNumerics(Distribution distribution) {
    CDF cdf = CDF.of(distribution);
    DiscreteCDF discreteCDF = (DiscreteCDF) cdf;
    assertEquals(cdf.p_lessEquals(RealScalar.of(-10)), RealScalar.ZERO);
    assertFalse(discreteCDF.cdf_finished());
    Scalar top = cdf.p_lessEquals(RealScalar.of(1000000));
    assertTrue(Scalars.lessThan( //
        top.subtract(RealScalar.ONE).abs(), //
        DiscreteCDF.CDF_NUMERIC_THRESHOLD));
    assertTrue(discreteCDF.cdf_finished());
  }

  public void testNumericsPoisson() {
    _checkDiscreteCDFNumerics(PoissonDistribution.of(RealScalar.of(.1)));
    _checkDiscreteCDFNumerics(PoissonDistribution.of(RealScalar.of(1.)));
    _checkDiscreteCDFNumerics(PoissonDistribution.of(RealScalar.of(70)));
    _checkDiscreteCDFNumerics(PoissonDistribution.of(RealScalar.of(700.)));
  }

  private static void _checkCDFNumerics(Distribution distribution) {
    CDF cdf = CDF.of(distribution);
    assertEquals(cdf.p_lessEquals(RealScalar.of(-10)), RealScalar.ZERO);
    Scalar top = cdf.p_lessEquals(RealScalar.of(1000000));
    assertTrue(Scalars.lessThan( //
        top.subtract(RealScalar.ONE).abs(), //
        DiscreteCDF.CDF_NUMERIC_THRESHOLD));
  }

  public void testNumericsGeometric() {
    _checkCDFNumerics(GeometricDistribution.of(RealScalar.of(.01)));
    _checkCDFNumerics(GeometricDistribution.of(RealScalar.of(.1)));
    _checkCDFNumerics(GeometricDistribution.of(RealScalar.of(.9)));
    _checkCDFNumerics(GeometricDistribution.of(RealScalar.of(.99)));
  }

  public void testFailPoisson() {
    try {
      PoissonDistribution.of(RealScalar.of(800));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
