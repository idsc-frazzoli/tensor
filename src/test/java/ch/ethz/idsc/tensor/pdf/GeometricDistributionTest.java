// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class GeometricDistributionTest extends TestCase {
  public void testPdf() {
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
    assertTrue(ExactScalarQ.of(large));
  }

  public void testFailP() {
    try {
      GeometricDistribution.of(RealScalar.ZERO);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      GeometricDistribution.of(RealScalar.of(1.1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testOne() {
    Distribution distribution = GeometricDistribution.of(RealScalar.ONE);
    assertEquals(PDF.of(distribution).at(RealScalar.ZERO), RealScalar.ONE);
    assertEquals(PDF.of(distribution).at(RealScalar.ONE), RealScalar.ZERO);
    assertEquals(RandomVariate.of(distribution), RealScalar.ZERO);
    assertEquals(Expectation.mean(distribution), RealScalar.ZERO);
    assertEquals(Expectation.variance(distribution), RealScalar.ZERO);
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

  public void testInverseCDF() {
    Distribution distribution = GeometricDistribution.of(RationalScalar.of(1, 3));
    InverseCDF inverseCDF = InverseCDF.of(distribution);
    assertEquals(inverseCDF.quantile(DoubleScalar.of(1)), DoubleScalar.POSITIVE_INFINITY);
    assertEquals(inverseCDF.quantile(RealScalar.ONE), DoubleScalar.POSITIVE_INFINITY);
  }

  public void testToString() {
    Distribution distribution = GeometricDistribution.of(RationalScalar.of(1, 3));
    assertEquals(distribution.toString(), "GeometricDistribution[1/3]");
  }

  public void testRandomVariate() {
    double P = 0.9999;
    AbstractDiscreteDistribution distribution = //
        (AbstractDiscreteDistribution) GeometricDistribution.of(RealScalar.of(P));
    {
      Scalar s = distribution.quantile(RealScalar.of(Math.nextDown(P)));
      assertEquals(s, RealScalar.ZERO);
    }
    {
      Scalar s = distribution.quantile(RealScalar.of(P));
      assertEquals(s, RealScalar.ONE);
    }
    {
      Scalar s = distribution.quantile(RealScalar.of(Math.nextDown(1.0)));
      assertEquals(s, RealScalar.of(3));
    }
  }

  public void testFailQuantile() {
    Distribution distribution = GeometricDistribution.of(RealScalar.of(.2));
    InverseCDF inverseCDF = InverseCDF.of(distribution);
    try {
      inverseCDF.quantile(RealScalar.of(-.1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      inverseCDF.quantile(RealScalar.of(1.1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNextDownOne() {
    for (int c = 500; c <= 700; c += 100) {
      Scalar p = DoubleScalar.of(.1 / c);
      // System.out.println(p);
      AbstractDiscreteDistribution distribution = //
          (AbstractDiscreteDistribution) GeometricDistribution.of(p);
      distribution.quantile(RealScalar.of(Math.nextDown(1.0)));
    }
  }

  public void testQuantity() {
    try {
      GeometricDistribution.of(Quantity.of(2, "s"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    final Scalar p = RationalScalar.of(1, 19);
    GeometricDistribution distribution = (GeometricDistribution) GeometricDistribution.of(p);
    try {
      PDF.of(distribution).at(Quantity.of(-2, "s")); // for now this returns 0
      // assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      CDF.of(distribution).p_lessEquals(Quantity.of(-2, "s"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
