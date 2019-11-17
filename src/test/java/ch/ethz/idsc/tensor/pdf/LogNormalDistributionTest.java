// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.io.IOException;
import java.util.Random;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.red.Mean;
import ch.ethz.idsc.tensor.red.Variance;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Clips;
import ch.ethz.idsc.tensor.sca.Exp;
import ch.ethz.idsc.tensor.sca.Sign;
import junit.framework.TestCase;

public class LogNormalDistributionTest extends TestCase {
  public void testSimple() throws ClassNotFoundException, IOException {
    LogNormalDistribution distribution = (LogNormalDistribution) Serialization.copy( //
        LogNormalDistribution.of(RationalScalar.HALF, RationalScalar.of(2, 3)));
    Chop._08.requireClose(Mean.of(distribution), Exp.FUNCTION.apply(RationalScalar.of(13, 18)));
    Chop._08.requireClose(Variance.of(distribution), RealScalar.of(2.372521698687904));
    {
      Scalar cdf = distribution.p_lessThan(RealScalar.of(0.7));
      Chop._08.requireClose(cdf, RealScalar.of(0.09939397268253057)); // confirmed with Mathematica
    }
    {
      Scalar cdf = distribution.p_lessThan(RealScalar.of(-0.7));
      Chop._08.requireClose(cdf, cdf.zero()); // confirmed with Mathematica
    }
    {
      Scalar cdf = distribution.p_lessEquals(RealScalar.of(0.7));
      Chop._08.requireClose(cdf, RealScalar.of(0.09939397268253057)); // confirmed with Mathematica
    }
    {
      Scalar pdf = distribution.at(RealScalar.of(0.7));
      Chop._08.requireClose(pdf, RealScalar.of(0.37440134735643077)); // confirmed with Mathematica
    }
    {
      Scalar pdf = distribution.at(RealScalar.of(-0.7));
      Chop._08.requireClose(pdf, pdf.zero()); // confirmed with Mathematica
    }
    Scalar quantile = distribution.quantile(RealScalar.of(0.4));
    Chop._03.requireClose(quantile, RealScalar.of(1.392501724505789));
    Random random = new Random();
    Scalar variate = distribution.randomVariate(random);
    Sign.requirePositive(variate);
    assertEquals(distribution.toString(), distribution.getClass().getSimpleName() + "[1/2, 2/3]");
  }

  public void testMean() {
    Distribution distribution = LogNormalDistribution.of(RationalScalar.of(4, 5), RationalScalar.of(2, 3));
    Scalar value = Mean.of(RandomVariate.of(distribution, 200)).Get();
    Clips.interval(2.4, 3.5).requireInside(value);
    Chop._10.requireClose(Mean.of(distribution), Exp.FUNCTION.apply(RationalScalar.of(46, 45)));
    Chop._08.requireClose(Variance.of(distribution), RealScalar.of(4.323016391513655));
  }

  public void testSigmaNonPositiveFail() {
    try {
      LogNormalDistribution.of(RationalScalar.HALF, RealScalar.ZERO);
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      LogNormalDistribution.of(RationalScalar.HALF, RationalScalar.of(-2, 3));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testQuantityFail() {
    try {
      LogNormalDistribution.of(Quantity.of(RationalScalar.HALF, "m"), RationalScalar.of(2, 3));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      LogNormalDistribution.of(RationalScalar.of(2, 3), Quantity.of(RationalScalar.HALF, "m"));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
