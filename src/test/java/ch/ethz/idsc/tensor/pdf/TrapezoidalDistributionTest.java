// code by clruch
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.qty.QuantityMagnitude;
import ch.ethz.idsc.tensor.red.Mean;
import junit.framework.TestCase;

public class TrapezoidalDistributionTest extends TestCase {
  final Random random = new Random();

  public void testPositive() {
    Scalar a = RationalScalar.of(random.nextInt(100), 1);
    Scalar b = a.add(RealScalar.of(random.nextDouble() * 10));
    Scalar c = b.add(RealScalar.of(random.nextDouble() * 10));
    Scalar d = c.add(RealScalar.of(random.nextDouble() * 10));
    Distribution distribution = TrapezoidalDistribution.of(a, b, c, d);
    for (int count = 0; count < 100; ++count) {
      Scalar scalar = RandomVariate.of(distribution);
      assertTrue(Scalars.lessEquals(RealScalar.ZERO, scalar));
    }
  }

  public void testPDF() {
    Scalar a = RationalScalar.of(1, 1);
    Scalar b = RationalScalar.of(2, 1);
    Scalar c = RationalScalar.of(3, 1);
    Scalar d = RationalScalar.of(4, 1);
    Distribution distribution = TrapezoidalDistribution.of(a, b, c, d);
    {
      Scalar actual = PDF.of(distribution).at(RealScalar.of(3));
      Scalar expected = RealScalar.of(2).divide(d.add(c).subtract(a).subtract(b));
      assertEquals(expected, actual);
    }
    {
      assertEquals(PDF.of(distribution).at(RealScalar.of(-3)), RealScalar.ZERO);
      assertEquals(PDF.of(distribution).at(RealScalar.of(13)), RealScalar.ZERO);
    }
  }

  public void testCDFPositive() {
    Scalar a = RealScalar.of(1);
    Scalar b = RealScalar.of(2);
    Scalar c = RealScalar.of(3);
    Scalar d = RealScalar.of(4);
    Distribution distribution = TrapezoidalDistribution.of(a, b, c, d);
    CDF cdf = CDF.of(distribution);
    assertEquals(cdf.p_lessEquals(RealScalar.of(-1)), RealScalar.ZERO);
    assertEquals(cdf.p_lessEquals(RealScalar.of(1.5)), RationalScalar.of(1, 16));
    assertEquals(cdf.p_lessEquals(RealScalar.of(+4)), RealScalar.ONE);
  }

  public void testMean() {
    Scalar a = RationalScalar.of(random.nextInt(100), 1);
    Scalar b = a.add(RealScalar.of(random.nextDouble() * 10));
    Scalar c = b.add(RealScalar.of(random.nextDouble() * 10));
    Scalar d = c.add(RealScalar.of(random.nextDouble() * 10));
    TrapezoidalDistribution distribution = (TrapezoidalDistribution) TrapezoidalDistribution.of(a, b, c, d);
    Tensor all = Tensors.empty();
    for (int i = 0; i < 3000; ++i) {
      Scalar s = RandomVariate.of(distribution);
      all.append(s);
    }
    Scalar meanCalc = distribution.mean();
    Scalar meanSamples = (Scalar) Mean.of(all);
    // System.out.println("meanCalc: " + meanCalc);
    // System.out.println("meanSamp: " + meanSamples);
    Scalar diff = meanCalc.subtract(meanSamples).abs();
    assertTrue(Scalars.lessEquals(diff, RealScalar.of(0.5)));
  }

  public void testQuantity() {
    Distribution distribution = //
        TrapezoidalDistribution.of(Quantity.of(1, "m"), Quantity.of(2, "m"), Quantity.of(3, "m"), Quantity.of(5, "m"));
    Scalar mean = Mean.of(distribution);
    assertEquals(mean, Scalars.fromString("14/5[m]"));
    assertTrue(ExactScalarQ.of(mean));
    PDF pdf = PDF.of(distribution);
    {
      Scalar density = pdf.at(Quantity.of(3, "m"));
      assertEquals(density, Scalars.fromString("2/5[m^-1]"));
    }
    CDF cdf = CDF.of(distribution);
    {
      Scalar prob = cdf.p_lessEquals(Quantity.of(4, "m"));
      ExactScalarQ.require(prob);
      assertEquals(prob, RationalScalar.of(9, 10));
    }
    {
      Scalar prob = cdf.p_lessEquals(Quantity.of(6, "m"));
      ExactScalarQ.require(prob);
      assertEquals(prob, RationalScalar.ONE);
    }
    Scalar random = RandomVariate.of(distribution);
    Scalar apply = QuantityMagnitude.SI().in("km").apply(random);
    assertTrue(apply instanceof RealScalar);
  }

  public void testQuantity2() {
    Distribution distribution = //
        TrapezoidalDistribution.of(Quantity.of(1, "m"), Quantity.of(2, "m"), Quantity.of(3, "m"), Quantity.of(4, "m"));
    Scalar mean = Mean.of(distribution);
    assertEquals(mean, Scalars.fromString("5/2[m]"));
    ExactScalarQ.require(mean);
    PDF pdf = PDF.of(distribution);
    {
      Scalar density = pdf.at(Scalars.fromString("3/2[m]"));
      ExactScalarQ.require(density);
      assertEquals(density, Scalars.fromString("1/4[m^-1]"));
    }
    {
      Scalar density = pdf.at(Quantity.of(2.5, "m"));
      ExactScalarQ.require(density);
      assertEquals(density, Scalars.fromString("1/2[m^-1]"));
    }
    {
      Scalar density = pdf.at(Scalars.fromString("7/2[m]"));
      ExactScalarQ.require(density);
      assertEquals(density, Scalars.fromString("1/4[m^-1]"));
    }
    CDF cdf = CDF.of(distribution);
    {
      Scalar prob = cdf.p_lessEquals(Quantity.of(4, "m"));
      ExactScalarQ.require(prob);
      assertEquals(prob, RationalScalar.of(10, 10));
    }
    {
      Scalar prob = cdf.p_lessEquals(Quantity.of(6, "m"));
      ExactScalarQ.require(prob);
      assertEquals(prob, RationalScalar.ONE);
    }
    Scalar random = RandomVariate.of(distribution);
    Scalar apply = QuantityMagnitude.SI().in("km").apply(random);
    assertTrue(apply instanceof RealScalar);
  }

  public void testExactFail() {
    TrapezoidalDistribution.of(Quantity.of(1, "m"), Quantity.of(2, "m"), Quantity.of(3, "m"), Quantity.of(3, "m"));
    TrapezoidalDistribution.of(Quantity.of(2, "m"), Quantity.of(2, "m"), Quantity.of(3, "m"), Quantity.of(3, "m"));
    try {
      TrapezoidalDistribution.of(Quantity.of(1, "m"), Quantity.of(1, "m"), Quantity.of(1, "m"), Quantity.of(1, "m"));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      TrapezoidalDistribution.of(Quantity.of(1, "m"), Quantity.of(2, "m"), Quantity.of(3, "m"), Quantity.of(1, "m"));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      TrapezoidalDistribution.of(Quantity.of(1, "m"), Quantity.of(2, "m"), Quantity.of(2, "m"), Quantity.of(5, "m"));
      // fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNumericFail() {
    TrapezoidalDistribution.of(Quantity.of(1., "m"), Quantity.of(2., "m"), Quantity.of(3., "m"), Quantity.of(3., "m"));
    TrapezoidalDistribution.of(Quantity.of(2., "m"), Quantity.of(2., "m"), Quantity.of(3., "m"), Quantity.of(3., "m"));
    try {
      TrapezoidalDistribution.of(Quantity.of(1., "m"), Quantity.of(1., "m"), Quantity.of(1., "m"), Quantity.of(1., "m"));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      TrapezoidalDistribution.of(Quantity.of(1., "m"), Quantity.of(2., "m"), Quantity.of(3., "m"), Quantity.of(1., "m"));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      TrapezoidalDistribution.of(Quantity.of(1., "m"), Quantity.of(2., "m"), Quantity.of(2., "m"), Quantity.of(5., "m"));
      // fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testCenterFail() {
    try {
      TrapezoidalDistribution.of(Quantity.of(1., "m"), Quantity.of(3., "m"), Quantity.of(2., "m"), Quantity.of(9., "m"));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
