// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.HashSet;
import java.util.Set;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.qty.QuantityTensor;
import ch.ethz.idsc.tensor.sca.Clip;
import junit.framework.TestCase;

public class HistogramDistributionTest extends TestCase {
  public void testSimple() {
    Distribution distribution = //
        HistogramDistribution.of(Tensors.vector(-3, -3, -2, -2, 10), RealScalar.of(2));
    PDF pdf = PDF.of(distribution);
    assertEquals(pdf.at(RealScalar.of(-3)), RationalScalar.of(2, 5));
    assertEquals(pdf.at(RealScalar.of(-4)), RationalScalar.of(2, 5));
    assertEquals(pdf.at(RealScalar.of(-4.1)), RealScalar.ZERO);
    assertEquals(pdf.at(RealScalar.ZERO), RealScalar.ZERO);
    assertEquals(pdf.at(RealScalar.of(11)), RationalScalar.of(1, 5));
    Clip c1 = Clip.function(-4, 0);
    Clip c2 = Clip.function(10, 12);
    Set<Scalar> set = new HashSet<>();
    for (int c = 0; c < 100; ++c) {
      Scalar x = RandomVariate.of(distribution);
      assertTrue(c1.isInside(x) || c2.isInside(x));
      set.add(x);
    }
    assertTrue(90 < set.size());
  }

  public void testQuantity() {
    Tensor vector = QuantityTensor.of(Tensors.vector(1, 1.7, 2, 3, 3.9, 4, 4.1), "m");
    Distribution distribution = //
        HistogramDistribution.of(vector, Quantity.of(0.7, "m"));
    assertTrue(RandomVariate.of(distribution) instanceof Quantity);
    PDF pdf = PDF.of(distribution);
    assertEquals(pdf.at(Quantity.of(0, "m")), RealScalar.ZERO);
    assertEquals(pdf.at(Quantity.of(1.2, "m")), RationalScalar.of(1, 7));
    assertEquals(pdf.at(Quantity.of(4.15, "m")), RationalScalar.of(3, 7));
    Clip clip = Clip.function(Quantity.of(0.7, "m"), Quantity.of(4.2, "m"));
    Set<Scalar> set = new HashSet<>();
    for (int c = 0; c < 100; ++c) {
      Scalar x = RandomVariate.of(distribution);
      assertTrue(clip.isInside(x));
      set.add(x);
    }
    assertTrue(90 < set.size());
  }

  public void testMean() {
    Tensor vector = QuantityTensor.of(Tensors.vector(1, 2, 3), "m");
    Distribution distribution = //
        HistogramDistribution.of(vector, Quantity.of(1, "m"));
    Scalar mean = Expectation.mean(distribution);
    assertEquals(mean, Quantity.of(2.5, "m"));
  }

  public void testVariance() {
    Tensor vector = QuantityTensor.of(Tensors.vector(1, 2, 3), "m");
    Distribution distribution = //
        HistogramDistribution.of(vector, Quantity.of(1, "m"));
    Scalar variance = Expectation.variance(distribution);
    // System.out.println(var);
    assertTrue(Scalars.lessEquals(Quantity.of(2 / 3.0, "m^2"), variance));
  }

  public void testFailEmpty() {
    try {
      HistogramDistribution.of(Tensors.empty(), RealScalar.of(2));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailWidth() {
    try {
      HistogramDistribution.of(Tensors.vector(1, 2, 3), RealScalar.ZERO);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      HistogramDistribution.of(Tensors.vector(1, 2, 3), RealScalar.of(-2));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
