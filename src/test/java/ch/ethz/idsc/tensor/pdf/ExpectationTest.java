// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.AbsSquared;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class ExpectationTest extends TestCase {
  private static void _check(Distribution distribution) {
    Scalar mean = Expectation.mean(distribution);
    {
      Scalar E_X = Expectation.of(s -> s, distribution);
      assertTrue(Chop._12.allZero(E_X.subtract(mean)));
    }
    Scalar var = Expectation.variance(distribution);
    {
      Scalar Var = Expectation.of(s -> AbsSquared.of(s.subtract(mean)), distribution);
      assertTrue(Chop._12.allZero(Var.subtract(var)));
    }
  }

  public void testExact() {
    _check(DiscreteUniformDistribution.of(4, 10));
    _check(BernoulliDistribution.of(RationalScalar.of(2, 7)));
    _check(BinomialDistribution.of(10, RationalScalar.of(3, 7)));
    _check(HypergeometricDistribution.of(10, 40, 100));
    _check(EmpiricalDistribution.fromUnscaledPDF(Tensors.vector(3, 2, 1, 4)));
  }

  public void testNumeric() {
    _check(PoissonDistribution.of(RationalScalar.of(4, 3)));
    _check(GeometricDistribution.of(RealScalar.of(.3)));
    _check(EmpiricalDistribution.fromUnscaledPDF(Tensors.vector(3, .2, 1, .4)));
  }
}
