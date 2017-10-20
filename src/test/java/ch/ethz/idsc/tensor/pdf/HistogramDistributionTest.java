// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.Clip;
import junit.framework.TestCase;

public class HistogramDistributionTest extends TestCase {
  public void testSimple() {
    Distribution distribution = //
        HistogramDistribution.of(Tensors.vector(-3, -3, -2, -2, 10), RealScalar.of(-10), RealScalar.of(2));
    PDF pdf = PDF.of(distribution);
    assertEquals(pdf.at(RealScalar.of(-3)), RationalScalar.of(2, 5));
    assertEquals(pdf.at(RealScalar.of(-4)), RationalScalar.of(2, 5));
    assertEquals(pdf.at(RealScalar.of(-4.1)), RealScalar.ZERO);
    assertEquals(pdf.at(RealScalar.ZERO), RealScalar.ZERO);
    assertEquals(pdf.at(RealScalar.of(11)), RationalScalar.of(1, 5));
    Clip c1 = Clip.function(-4, 0);
    Clip c2 = Clip.function(10, 12);
    for (int c = 0; c < 100; ++c) {
      Scalar x = RandomVariate.of(distribution);
      assertTrue(c1.isInside(x) || c2.isInside(x));
    }
  }

  public void testFailMinimum() {
    try {
      HistogramDistribution.of(Tensors.vector(-3, -3, -2, -2, 10), RealScalar.of(-2), RealScalar.of(2));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFail() {
    try {
      HistogramDistribution.of(Tensors.empty(), RealScalar.of(-10), RealScalar.of(2));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
