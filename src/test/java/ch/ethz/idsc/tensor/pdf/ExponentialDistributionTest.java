// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.MachineNumberQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.qty.Unit;
import ch.ethz.idsc.tensor.qty.UnitConvert;
import ch.ethz.idsc.tensor.red.Mean;
import ch.ethz.idsc.tensor.sca.Exp;
import ch.ethz.idsc.tensor.sca.Sign;
import junit.framework.TestCase;

public class ExponentialDistributionTest extends TestCase {
  public void testPositive() {
    Distribution distribution = ExponentialDistribution.of(RealScalar.ONE);
    for (int c = 0; c < 100; ++c) {
      Scalar s = RandomVariate.of(distribution);
      assertTrue(Scalars.lessEquals(RealScalar.ZERO, s));
    }
  }

  public void testPDF() {
    Distribution distribution = ExponentialDistribution.of(2);
    {
      Scalar actual = PDF.of(distribution).at(RealScalar.of(3));
      Scalar expected = RealScalar.of(2).divide(Exp.of(RealScalar.of(6)));
      assertEquals(expected, actual);
    }
    {
      Scalar actual = PDF.of(distribution).at(RealScalar.of(-3));
      assertEquals(actual, RealScalar.ZERO);
    }
  }

  public void testCDFPositive() {
    Distribution distribution = ExponentialDistribution.of(RealScalar.of(2));
    CDF cdf = CDF.of(distribution);
    Scalar actual = cdf.p_lessEquals(RealScalar.of(3));
    Scalar expected = RealScalar.ONE.subtract(Exp.of(RealScalar.of(6)).reciprocal());
    assertEquals(expected, actual);
  }

  public void testCDFNegative() {
    Distribution distribution = ExponentialDistribution.of(RealScalar.ONE);
    CDF cdf = CDF.of(distribution);
    assertEquals(cdf.p_lessThan(RealScalar.of(-1)), RealScalar.ZERO);
    assertEquals(cdf.p_lessEquals(RealScalar.of(-1)), RealScalar.ZERO);
    assertEquals(cdf.p_lessThan(RealScalar.of(0)), RealScalar.ZERO);
    assertEquals(cdf.p_lessEquals(RealScalar.of(0)), RealScalar.ZERO);
  }

  public void testMean() {
    Scalar lambda = RealScalar.of(2);
    Distribution distribution = ExponentialDistribution.of(lambda);
    Tensor all = Tensors.empty();
    for (int c = 0; c < 2000; ++c) {
      Scalar s = RandomVariate.of(distribution);
      all.append(s);
    }
    Scalar mean = lambda.reciprocal();
    assertEquals(Expectation.mean(distribution), mean);
    Scalar diff = Mean.of(all).Get().subtract(mean).abs();
    assertTrue(Scalars.lessThan(diff, RealScalar.of(0.05)));
  }

  public void testFailL() {
    try {
      ExponentialDistribution.of(RealScalar.ZERO);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      ExponentialDistribution.of(RealScalar.of(-.1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNextUp() {
    double zero = 0;
    double nonzero = Math.nextUp(zero);
    double log = Math.log(nonzero);
    assertTrue(-2000 < log);
  }

  private static void _checkCorner(Scalar lambda) {
    ExponentialDistribution exponentialDistribution = (ExponentialDistribution) ExponentialDistribution.of(lambda);
    Scalar from0 = exponentialDistribution.randomVariate(0);
    assertTrue(MachineNumberQ.of(from0));
    assertTrue(Scalars.lessThan(RealScalar.ZERO, from0));
    double max = Math.nextDown(1.0);
    Scalar from1 = exponentialDistribution.randomVariate(max);
    assertTrue(Scalars.lessEquals(RealScalar.ZERO, from1));
    assertFalse(Scalars.lessThan(RealScalar.ZERO, exponentialDistribution.randomVariate(1)));
  }

  public void testCornerCase() {
    _checkCorner(RealScalar.of(.00001));
    _checkCorner(RealScalar.of(.1));
    _checkCorner(RealScalar.of(1));
    _checkCorner(RealScalar.of(2));
    _checkCorner(RealScalar.of(700));
  }

  public void testQuantity() {
    Distribution distribution = ExponentialDistribution.of(Quantity.of(3, "m"));
    Scalar rand = RandomVariate.of(distribution);
    assertTrue(rand instanceof Quantity);
    UnitConvert.SI().to(Unit.of("mi^-1")).apply(rand);
    assertTrue(Expectation.mean(distribution) instanceof Quantity);
    Scalar var = Expectation.variance(distribution);
    assertTrue(var instanceof Quantity);
  }

  public void testInverseCDF() {
    InverseCDF inverseCDF = InverseCDF.of(ExponentialDistribution.of(Quantity.of(3, "")));
    Scalar x0 = inverseCDF.quantile(RealScalar.of(.0));
    Scalar x1 = inverseCDF.quantile(RealScalar.of(.2));
    Scalar x2 = inverseCDF.quantile(RealScalar.of(.5));
    assertEquals(x0, RealScalar.ZERO);
    assertTrue(Scalars.lessThan(x1, x2));
  }

  public void testInverseCDF_1() {
    InverseCDF inverseCDF = InverseCDF.of(ExponentialDistribution.of(Quantity.of(2.8, "")));
    assertEquals(inverseCDF.quantile(RealScalar.of(1.0)), DoubleScalar.POSITIVE_INFINITY);
    assertEquals(inverseCDF.quantile(RealScalar.ONE), DoubleScalar.POSITIVE_INFINITY);
  }

  public void testToString() {
    Distribution distribution = ExponentialDistribution.of(Quantity.of(3, "m"));
    String string = distribution.toString();
    assertEquals(string, "ExponentialDistribution[3[m]]");
  }

  public void testFailInverseCDF() {
    InverseCDF inverseCDF = InverseCDF.of(ExponentialDistribution.of(Quantity.of(3, "")));
    try {
      inverseCDF.quantile(RealScalar.of(1.1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testQuantityPDF() {
    Distribution distribution = ExponentialDistribution.of(Quantity.of(3, "m"));
    {
      Scalar prob = PDF.of(distribution).at(Quantity.of(2, "m^-1"));
      assertTrue(Sign.isPositive(prob));
      assertTrue(prob instanceof Quantity);
    }
    {
      Scalar prob = PDF.of(distribution).at(Quantity.of(-2, "m^-1"));
      assertEquals(prob.toString(), "0[m]");
    }
  }

  public void testQuantityCDF() {
    Distribution distribution = ExponentialDistribution.of(Quantity.of(3, "m"));
    {
      Scalar prob = CDF.of(distribution).p_lessThan(Quantity.of(2, "m^-1"));
      assertTrue(Sign.isPositive(prob));
      assertTrue(prob instanceof RealScalar);
    }
    {
      Scalar prob = CDF.of(distribution).p_lessEquals(Quantity.of(-2, "m^-1"));
      assertEquals(prob.toString(), "0");
    }
  }
}
