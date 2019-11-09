// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.io.IOException;
import java.util.Random;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.red.Mean;
import ch.ethz.idsc.tensor.red.Variance;
import ch.ethz.idsc.tensor.sca.Chop;
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
  }
}
