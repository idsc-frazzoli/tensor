// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.NumberQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.qty.QuantityMagnitude;
import ch.ethz.idsc.tensor.qty.Unit;
import ch.ethz.idsc.tensor.qty.UnitConvert;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class FrechetDistributionTest extends TestCase {
  public void testPDF() {
    Distribution distribution = //
        FrechetDistribution.of(RealScalar.of(1.3), RealScalar.of(1.2));
    PDF pdf = PDF.of(distribution);
    Scalar prob = pdf.at(RealScalar.of(2.9));
    assertTrue(Chop._13.close(prob, RealScalar.of(0.10362186999648638)));
    assertFalse(pdf.at(RealScalar.of(20000)).equals(RealScalar.ZERO));
  }

  public void testCDF() {
    Distribution distribution = FrechetDistribution.of(1.5, 1.3);
    CDF cdf = CDF.of(distribution);
    Scalar prob = cdf.p_lessEquals(RealScalar.of(2.3));
    assertTrue(Chop._13.close(prob, RealScalar.of(0.6538117883387893)));
  }

  public void testRandomVariate() {
    FrechetDistribution gmd = (FrechetDistribution) FrechetDistribution.of(3, .2);
    assertTrue(Scalars.lessThan(gmd.randomVariate(0), RealScalar.of(0.1)));
    assertTrue(Scalars.lessThan(gmd.randomVariate(Math.nextDown(1.0)), RealScalar.of(42000)));
  }

  public void testQuantity() {
    Distribution distribution = FrechetDistribution.of(Quantity.of(1.3, ""), Quantity.of(1.4, "m^-1"));
    Scalar rand = RandomVariate.of(distribution);
    assertTrue(rand instanceof Quantity);
    UnitConvert.SI().to(Unit.of("in^-1")).apply(rand);
    {
      Scalar prob = PDF.of(distribution).at(Quantity.of(1, "m^-1"));
      QuantityMagnitude.SI().in(Unit.of("in")).apply(prob);
    }
    {
      CDF cdf = CDF.of(distribution);
      Scalar prob = cdf.p_lessEquals(Quantity.of(10, "m^-1"));
      assertTrue(prob instanceof DoubleScalar);
      assertTrue(NumberQ.of(prob));
    }
  }

  public void testMean() {
    Distribution distribution = //
        FrechetDistribution.of(Quantity.of(1.3, ""), Quantity.of(2.4, "m^-1"));
    Scalar mean = Expectation.mean(distribution);
    assertTrue(Chop._13.close(mean, Quantity.of(9.470020440153482, "m^-1")));
  }

  public void testMeanInf() {
    Distribution distribution = //
        FrechetDistribution.of(RealScalar.of(0.9), Quantity.of(2.4, "m^-1"));
    Scalar mean = Expectation.mean(distribution);
    assertEquals(mean, Quantity.of(DoubleScalar.POSITIVE_INFINITY, "m^-1"));
  }

  public void testVariance() {
    Distribution distribution = //
        FrechetDistribution.of(Quantity.of(2.3, ""), Quantity.of(1.5, "m^-1"));
    Scalar var = Expectation.variance(distribution);
    assertTrue(Chop._13.close(var, Quantity.of(10.631533530833654, "m^-2")));
  }

  public void testVarianceInf() {
    Distribution distribution = //
        FrechetDistribution.of(RealScalar.of(1.3), Quantity.of(1.5, "m^-1"));
    Scalar var = Expectation.variance(distribution);
    assertEquals(var, Quantity.of(Double.POSITIVE_INFINITY, "m^-2"));
    assertEquals(CDF.of(distribution).p_lessThan(RealScalar.ZERO), RealScalar.ZERO);
    assertEquals(CDF.of(distribution).p_lessEquals(RealScalar.ZERO), RealScalar.ZERO);
  }

  public void testInverseCDF() {
    InverseCDF inverseCDF = InverseCDF.of(FrechetDistribution.of(1.5, 1.3));
    Scalar x0 = inverseCDF.quantile(RealScalar.of(.0));
    Scalar x1 = inverseCDF.quantile(RealScalar.of(.1));
    Scalar x2 = inverseCDF.quantile(RealScalar.of(.2));
    Scalar x3 = inverseCDF.quantile(RealScalar.of(.5));
    assertEquals(x0, RealScalar.ZERO);
    assertTrue(Scalars.lessThan(x0, x1));
    assertTrue(Scalars.lessThan(x1, x2));
    assertTrue(Scalars.lessThan(x2, x3));
  }

  public void testInverseCDF_1() {
    InverseCDF inverseCDF = InverseCDF.of(FrechetDistribution.of(1.5, 1.3));
    try {
      inverseCDF.quantile(RealScalar.of(1.0));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailInverseCDF() {
    InverseCDF inverseCDF = InverseCDF.of(FrechetDistribution.of(1.5, 1.3));
    try {
      inverseCDF.quantile(RealScalar.of(1.1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFail() {
    try {
      FrechetDistribution.of(RealScalar.of(3), RealScalar.of(0));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      FrechetDistribution.of(RealScalar.of(0), RealScalar.of(2));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      FrechetDistribution.of(Quantity.of(2.3, "s"), Quantity.of(1.5, "m^-1"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
