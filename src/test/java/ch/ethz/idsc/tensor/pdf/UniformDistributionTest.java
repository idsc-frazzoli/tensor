// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.qty.QuantityMagnitude;
import ch.ethz.idsc.tensor.qty.Unit;
import junit.framework.TestCase;

public class UniformDistributionTest extends TestCase {
  public void testSimple() {
    CDF cdf = CDF.of(UniformDistribution.of(RealScalar.ONE, RealScalar.of(3)));
    assertEquals(cdf.p_lessThan(RealScalar.ONE), RealScalar.ZERO);
    assertEquals(cdf.p_lessThan(RealScalar.of(2)), RationalScalar.of(1, 2));
    assertEquals(cdf.p_lessThan(RealScalar.of(3)), RealScalar.ONE);
    assertEquals(cdf.p_lessThan(RealScalar.of(4)), RealScalar.ONE);
  }

  public void testUnit() {
    UniformDistribution distribution = (UniformDistribution) UniformDistribution.unit();
    assertEquals(distribution.mean(), RationalScalar.of(1, 2));
    assertEquals(distribution.variance(), RationalScalar.of(1, 12));
  }

  public void testRandomVariate() {
    Scalar s1 = RandomVariate.of(UniformDistribution.of(0, 1), new Random(1000));
    Scalar s2 = RandomVariate.of(UniformDistribution.unit(), new Random(1000));
    assertEquals(s1, s2);
  }

  public void testQuantity() {
    Distribution distribution = UniformDistribution.of(Quantity.of(3, "g"), Quantity.of(5, "g"));
    assertTrue(RandomVariate.of(distribution) instanceof Quantity);
    Scalar mean = Expectation.mean(distribution);
    assertTrue(mean instanceof Quantity);
    assertEquals(mean, Quantity.of(4, "g"));
    Scalar var = Expectation.variance(distribution);
    assertTrue(var instanceof Quantity);
    assertEquals(var, Scalars.fromString("1/3[g^2]"));
    {
      Scalar prob = PDF.of(distribution).at(mean);
      QuantityMagnitude.SI().in(Unit.of("lb^-1")).apply(prob);
    }
    assertEquals(CDF.of(distribution).p_lessEquals(mean), RationalScalar.of(1, 2));
  }

  public void testQuantile() {
    Distribution distribution = UniformDistribution.of(Quantity.of(3, "g"), Quantity.of(6, "g"));
    InverseCDF inverseCDF = InverseCDF.of(distribution);
    assertEquals(inverseCDF.quantile(RationalScalar.of(0, 3)), Quantity.of(3, "g"));
    assertEquals(inverseCDF.quantile(RationalScalar.of(1, 3)), Quantity.of(4, "g"));
    assertEquals(inverseCDF.quantile(RationalScalar.of(2, 3)), Quantity.of(5, "g"));
    assertEquals(inverseCDF.quantile(RationalScalar.of(3, 3)), Quantity.of(6, "g"));
  }

  public void testQuantileFail() {
    Distribution distribution = UniformDistribution.of(Quantity.of(3, "g"), Quantity.of(6, "g"));
    InverseCDF inverseCDF = InverseCDF.of(distribution);
    try {
      inverseCDF.quantile(RealScalar.of(-0.1));
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

  public void testQuantityFail() {
    try {
      UniformDistribution.of(Quantity.of(3, "m"), Quantity.of(5, "km"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFail() {
    try {
      UniformDistribution.of(RealScalar.ONE, RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      UniformDistribution.of(RealScalar.ONE, RealScalar.ZERO);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
