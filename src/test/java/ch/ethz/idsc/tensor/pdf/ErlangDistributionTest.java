// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.qty.Unit;
import ch.ethz.idsc.tensor.qty.Units;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class ErlangDistributionTest extends TestCase {
  public void testPdf() {
    Distribution distribution = ErlangDistribution.of(3, RealScalar.of(1.8));
    PDF pdf = PDF.of(distribution);
    Scalar p = pdf.at(RealScalar.of(3.2));
    assertTrue(Chop._06.close(p, RealScalar.of(0.0940917)));
    assertEquals(pdf.at(RealScalar.of(0)), RealScalar.ZERO);
    assertEquals(pdf.at(RealScalar.of(-0.12)), RealScalar.ZERO);
  }

  public void testMean() {
    Distribution distribution = ErlangDistribution.of(5, Quantity.of(10, "m"));
    Scalar mean = Expectation.mean(distribution);
    assertEquals(mean, Scalars.fromString("1/2[m^-1]"));
  }

  public void testVariance() {
    Distribution distribution = ErlangDistribution.of(5, Quantity.of(10, "m"));
    Scalar var = Expectation.variance(distribution);
    assertEquals(var, Scalars.fromString("1/20[m^-2]"));
  }

  public void testQuantityPDF() {
    Distribution distribution = ErlangDistribution.of(4, Quantity.of(6, "m"));
    PDF pdf = PDF.of(distribution);
    {
      Scalar prob = pdf.at(Quantity.of(1.2, "m^-1"));
      assertEquals(Units.of(prob), Unit.of("m"));
    }
    {
      Scalar prob = pdf.at(Quantity.of(-1.2, "m^-1"));
      assertTrue(prob instanceof Quantity);
      assertEquals(Units.of(prob), Unit.of("m"));
    }
  }

  public void testFail() {
    try {
      ErlangDistribution.of(0, RealScalar.of(1.8));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
