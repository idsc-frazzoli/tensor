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

public class GumbelDistributionTest extends TestCase {
  public void testPDF() {
    Distribution distribution = //
        GumbelDistribution.of(RealScalar.of(3), RealScalar.of(.2));
    PDF pdf = PDF.of(distribution);
    Scalar prob = pdf.at(RealScalar.of(2.9));
    assertTrue(Chop._13.close(prob, RealScalar.of(1.65352149445209)));
    assertEquals(pdf.at(RealScalar.of(4.5)), RealScalar.ZERO);
  }

  public void testCDF() {
    Distribution distribution = //
        GumbelDistribution.of(RealScalar.of(3), RealScalar.of(.2));
    CDF cdf = CDF.of(distribution);
    Scalar prob = cdf.p_lessEquals(RealScalar.of(2.9));
    assertTrue(Chop._13.close(prob, RealScalar.of(0.45476078810739484)));
    assertEquals(cdf.p_lessEquals(RealScalar.of(4)), RealScalar.ONE);
  }

  public void testRandomVariate() {
    GumbelDistribution gmd = (GumbelDistribution) //
    GumbelDistribution.of(RealScalar.of(3), RealScalar.of(.2));
    assertTrue(Scalars.lessThan(gmd.randomVariate(0), RealScalar.of(4.5)));
    assertTrue(Scalars.lessThan(RealScalar.of(-4.5), gmd.randomVariate(Math.nextDown(1.0))));
  }

  public void testQuantity() {
    Distribution distribution = GumbelDistribution.of(Quantity.of(.3, "m^-1"), Quantity.of(.4, "m^-1"));
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
        GumbelDistribution.of(Quantity.of(-.3, "m^-1"), Quantity.of(.4, "m^-1"));
    Scalar mean = Expectation.mean(distribution);
    assertTrue(Chop._13.close(mean, Quantity.of(-0.5308862659606132, "m^-1")));
  }

  public void testVariance() {
    Distribution distribution = //
        GumbelDistribution.of(Quantity.of(-1.3, "m^-1"), Quantity.of(1.5, "m^-1"));
    Scalar var = Expectation.variance(distribution);
    assertTrue(Chop._13.close(var, Quantity.of(3.7011016504085092, "m^-2")));
  }

  public void testToString() {
    Distribution distribution = //
        GumbelDistribution.of(RealScalar.of(3), RealScalar.of(.2));
    assertEquals(distribution.toString(), "GumbelDistribution[3, 0.2]");
  }

  public void testFail() {
    try {
      GumbelDistribution.of(RealScalar.of(3), RealScalar.of(0));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
