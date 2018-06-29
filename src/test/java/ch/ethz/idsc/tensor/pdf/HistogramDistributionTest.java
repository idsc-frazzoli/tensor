// code by jph and gjoel
package ch.ethz.idsc.tensor.pdf;

import java.util.HashSet;
import java.util.Set;

import ch.ethz.idsc.tensor.ExactScalarQ;
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
  public void testPdf() {
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

  public void testFreedman() {
    Tensor samples = Tensors.vector(-4, -3, -3, -2, -2, 10);
    Distribution distribution = HistogramDistribution.of(samples, BinningMethod.IQR);
    PDF pdf = PDF.of(distribution);
    assertTrue(Scalars.nonZero(pdf.at(RealScalar.of(-3))));
    assertTrue(Scalars.lessThan(RealScalar.ONE, BinningMethod.IQR.apply(samples)));
  }

  public void testFreedmanMin() {
    HistogramDistribution.of(Tensors.vector(3, 4));
    try {
      HistogramDistribution.of(Tensors.vector(3, 3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testScott() {
    Tensor samples = Tensors.vector(-4, -3, -3, -2, -2, 10);
    Distribution distribution = HistogramDistribution.of(samples, BinningMethod.VARIANCE);
    PDF pdf = PDF.of(distribution);
    assertTrue(Scalars.nonZero(pdf.at(RealScalar.of(-3))));
    assertTrue(Scalars.lessThan(RealScalar.ONE, BinningMethod.VARIANCE.apply(samples)));
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
    Distribution distribution = HistogramDistribution.of(vector, Quantity.of(1, "m"));
    assertEquals( //
        Expectation.variance(distribution), //
        Quantity.of( //
            Expectation.variance(UniformDistribution.of(0, 3)), "m^2"));
  }

  public void testVariance1() {
    assertEquals( //
        Expectation.variance(HistogramDistribution.of(Tensors.vector(0.5), RealScalar.of(1))), //
        RationalScalar.of(1, 12));
    assertEquals( //
        Expectation.variance(HistogramDistribution.of(Tensors.vector(0.5), RealScalar.of(2))), //
        Expectation.variance(UniformDistribution.of(0, 2)));
  }

  public void testVariance2() {
    assertEquals( //
        Expectation.variance(HistogramDistribution.of(Tensors.vector(0.5, 1.5), RealScalar.of(1))), //
        Expectation.variance(UniformDistribution.of(0, 2)));
    assertEquals( //
        Expectation.variance(HistogramDistribution.of(Tensors.vector(0.5, 2.5), RealScalar.of(2))), //
        Expectation.variance(UniformDistribution.of(0, 4)));
  }

  public void testVarianceIr1() {
    assertEquals(Expectation.variance(HistogramDistribution.of(Tensors.vector(0.5, 1.5, 1.5), RealScalar.of(1))), RationalScalar.of(11, 36));
    assertEquals(Expectation.variance(HistogramDistribution.of(Tensors.vector(2.5, 1.5, 1.5), RealScalar.of(1))), RationalScalar.of(11, 36));
  }

  public void testVarianceIr2() {
    assertEquals(Expectation.variance(HistogramDistribution.of(Tensors.vector(4.5, 4.5, 2.5), RealScalar.of(2))), RationalScalar.of(11, 9));
    assertEquals(Expectation.variance(HistogramDistribution.of(Tensors.vector(2.5, 1.5, 1.5), RealScalar.of(2))), RationalScalar.of(11, 9));
  }

  public void testVarianceIr3() {
    assertEquals(Expectation.variance(HistogramDistribution.of(Tensors.vector(4.5, 4.5, 2.5, 10.5), RealScalar.of(2))), RationalScalar.of(28, 3));
    assertEquals(Expectation.variance(HistogramDistribution.of(Tensors.vector(2.5, 1.5, 1.5, 10.5), RealScalar.of(2))), RationalScalar.of(52, 3));
  }

  public void testCDF() {
    Distribution distribution = HistogramDistribution.of(Tensors.vector(0.5, 1.5, 1.5, 2.2), RealScalar.of(1));
    CDF cdf = CDF.of(distribution);
    assertEquals(cdf.p_lessThan(RationalScalar.of(0, 1)), RationalScalar.of(0, 1));
    assertEquals(cdf.p_lessThan(RationalScalar.of(1, 1)), RationalScalar.of(1, 4));
    assertEquals(cdf.p_lessThan(RationalScalar.of(3, 2)), RationalScalar.of(1, 2));
    assertEquals(cdf.p_lessThan(RationalScalar.of(2, 1)), RationalScalar.of(3, 4));
    assertEquals(cdf.p_lessThan(RationalScalar.of(3, 1)), RationalScalar.of(1, 1));
  }

  public void testQuantityCDF() {
    Tensor vector = QuantityTensor.of(Tensors.vector(1, 2, 3), "m");
    Distribution distribution = HistogramDistribution.of(vector, Quantity.of(2, "m"));
    CDF cdf = CDF.of(distribution);
    assertEquals(cdf.p_lessEquals(Quantity.of(2, "m")), RationalScalar.of(1, 3));
    assertEquals(cdf.p_lessEquals(Quantity.of(3, "m")), RationalScalar.of(2, 3));
  }

  public void testInverseCDF() {
    Distribution distribution = HistogramDistribution.of(Tensors.vector(0.5, 1.5, 1.5, 2.2), RealScalar.of(1));
    InverseCDF inverseCDF = InverseCDF.of(distribution);
    {
      Scalar x = inverseCDF.quantile(RealScalar.ZERO);
      assertTrue(ExactScalarQ.of(x));
      assertEquals(x, RealScalar.ZERO);
    }
    {
      Scalar x = inverseCDF.quantile(RationalScalar.of(1, 2));
      assertTrue(ExactScalarQ.of(x));
      assertEquals(x, RationalScalar.of(3, 2));
    }
    {
      Scalar x = inverseCDF.quantile(RationalScalar.of(1, 4));
      assertTrue(ExactScalarQ.of(x));
      assertEquals(x, RationalScalar.of(1, 1));
    }
    {
      Scalar x = inverseCDF.quantile(RationalScalar.of(3, 4));
      assertTrue(ExactScalarQ.of(x));
      assertEquals(x, RationalScalar.of(2, 1));
    }
  }

  public void testInverseCDF2() {
    Tensor vector = QuantityTensor.of(Tensors.vector(1, 2, 3), "m");
    Distribution distribution = HistogramDistribution.of(vector, Quantity.of(2, "m"));
    InverseCDF inverseCDF = InverseCDF.of(distribution);
    {
      Scalar x = inverseCDF.quantile(RealScalar.ZERO);
      assertTrue(ExactScalarQ.of(x));
      assertEquals(x, Quantity.of(0, "m"));
    }
    {
      Scalar x = inverseCDF.quantile(RationalScalar.of(2, 3));
      assertTrue(ExactScalarQ.of(x));
      assertEquals(x, Quantity.of(RationalScalar.of(3, 1), "m"));
    }
    {
      Scalar x = inverseCDF.quantile(RationalScalar.of(1, 2));
      assertTrue(ExactScalarQ.of(x));
      assertEquals(x, Quantity.of(RationalScalar.of(5, 2), "m"));
    }
  }

  public void testInverseCDFOne() {
    Tensor vector = QuantityTensor.of(Tensors.vector(1, 2, 3), "m");
    Distribution distribution = HistogramDistribution.of(vector, Quantity.of(2, "m"));
    InverseCDF inverseCDF = InverseCDF.of(distribution);
    assertEquals(inverseCDF.quantile(RealScalar.ONE), Quantity.of(4, "m"));
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
