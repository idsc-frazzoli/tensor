// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Map;
import java.util.NavigableMap;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.Tally;
import ch.ethz.idsc.tensor.sca.N;
import junit.framework.TestCase;

public class BernoulliDistributionTest extends TestCase {
  public void testEquals() {
    Scalar p = RationalScalar.of(1, 3);
    Distribution distribution = BernoulliDistribution.of(p);
    PDF pdf = PDF.of(distribution);
    // PDF[BernoulliDistribution[1/3], 0] == 2/3
    assertEquals(pdf.at(RealScalar.of(0)), RationalScalar.of(2, 3));
    assertEquals(pdf.at(RealScalar.of(1)), RationalScalar.of(1, 3));
    assertEquals(pdf.at(RealScalar.of(2)), RealScalar.ZERO);
  }

  public void testLessThan() {
    Scalar p = RationalScalar.of(1, 3);
    Distribution distribution = BernoulliDistribution.of(p);
    CDF cdf = CDF.of(distribution);
    assertEquals(cdf.p_lessThan(RealScalar.of(0)), RationalScalar.ZERO);
    assertEquals(cdf.p_lessThan(RealScalar.of(1)), RationalScalar.of(2, 3));
    assertEquals(cdf.p_lessThan(RealScalar.of(2)), RealScalar.ONE);
  }

  public void testLessEquals() {
    Scalar p = RationalScalar.of(1, 3);
    Distribution distribution = BernoulliDistribution.of(p);
    CDF cdf = CDF.of(distribution);
    assertEquals(cdf.p_lessEquals(RealScalar.of(0)), RationalScalar.of(2, 3));
    assertEquals(cdf.p_lessEquals(RealScalar.of(1)), RationalScalar.ONE);
    assertEquals(cdf.p_lessEquals(RealScalar.of(2)), RealScalar.ONE);
  }

  public void testSample() {
    final Scalar p = RationalScalar.of(1, 3);
    Distribution distribution = BernoulliDistribution.of(p);
    Tensor list = RandomVariate.of(distribution, 2000);
    Map<Tensor, Long> map = Tally.of(list);
    long v0 = map.get(RealScalar.ZERO);
    long v1 = map.get(RealScalar.ONE);
    Scalar ratio = RationalScalar.of(v1, v0 + v1);
    Scalar dev = N.DOUBLE.of(ratio.subtract(p).abs());
    assertTrue(Scalars.lessThan(dev, RealScalar.of(.07)));
  }

  public void testInverseCdf() {
    Scalar p = RationalScalar.of(1, 3);
    EvaluatedDiscreteDistribution distribution = (EvaluatedDiscreteDistribution) BernoulliDistribution.of(p);
    NavigableMap<Scalar, Scalar> map = distribution.inverse_cdf();
    assertEquals(map.get(RationalScalar.of(2, 3)), RealScalar.ZERO);
    assertEquals(map.get(RationalScalar.of(1, 1)), RealScalar.ONE);
  }

  public void testNumber() {
    Distribution distribution = BernoulliDistribution.of(0.5);
    InverseCDF inverseCDF = InverseCDF.of(distribution);
    double half = 0.5;
    assertEquals(inverseCDF.quantile(RealScalar.of(half)), RealScalar.ONE);
    assertEquals(inverseCDF.quantile(RealScalar.of(Math.nextDown(half))), RealScalar.ZERO);
    try {
      inverseCDF.quantile(RealScalar.of(-0.1));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      inverseCDF.quantile(RealScalar.of(1.1));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailP() {
    try {
      BernoulliDistribution.of(RationalScalar.of(-1, 3));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      BernoulliDistribution.of(RationalScalar.of(4, 3));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailPNumber() {
    try {
      BernoulliDistribution.of(-1e-10);
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      BernoulliDistribution.of(1.0001);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
