// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.qty.QuantityTensor;
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

  public void testQuantity() {
    Tensor vector = QuantityTensor.of(Tensors.vector(1, 1.7, 2, 3, 3.9, 4, 4.1), "m");
    Distribution distribution = //
        HistogramDistribution.of(vector, Quantity.of(1, "m"), Quantity.of(0.7, "m"));
    assertTrue(RandomVariate.of(distribution) instanceof Quantity);
    PDF pdf = PDF.of(distribution);
    assertEquals(pdf.at(Quantity.of(0, "m")), RealScalar.ZERO);
    assertEquals(pdf.at(Quantity.of(1.2, "m")), RationalScalar.of(1, 7));
    assertEquals(pdf.at(Quantity.of(4.2, "m")), RationalScalar.of(3, 7));
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
