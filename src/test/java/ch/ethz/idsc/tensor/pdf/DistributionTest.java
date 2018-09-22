// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.Mean;
import ch.ethz.idsc.tensor.red.Variance;
import ch.ethz.idsc.tensor.sca.Sqrt;
import junit.framework.TestCase;

public class DistributionTest extends TestCase {
  private static void _check(Distribution distribution, int n) {
    Tensor collect = RandomVariate.of(distribution, n);
    Scalar mean = Mean.of(collect).Get(); // measured mean
    Scalar var = Variance.ofVector(collect).Get(); // measured variance
    assertTrue(Scalars.nonZero(var));
    Scalar tmean = Expectation.mean(distribution); // theoretical mean
    Scalar tvar = Expectation.variance(distribution); // theoretical variance
    Scalar dmean = mean.subtract(tmean).divide(tmean).abs();
    // LONGTERM https://en.wikipedia.org/wiki/Central_limit_theorem
    @SuppressWarnings("unused")
    Scalar limmean = Sqrt.of(RealScalar.of(n)).multiply(mean.subtract(tmean)).divide(Sqrt.of(tvar));
    Scalar dvar = var.subtract(tvar).divide(tvar).abs();
    assertTrue(Scalars.lessThan(dmean, RealScalar.of(.2)));
    assertTrue(Scalars.lessThan(dvar, RealScalar.of(.22)));
  }

  public void testDiscrete() {
    _check(BernoulliDistribution.of(RationalScalar.of(2, 3)), 1000);
    _check(BinomialDistribution.of(10, RationalScalar.of(1, 4)), 1000);
    _check(BinomialDistribution.of(10, RationalScalar.of(7, 8)), 1000);
    _check(DiscreteUniformDistribution.of(RealScalar.of(10), RealScalar.of(30)), 1000);
    _check(GeometricDistribution.of(RealScalar.of(0.7)), 2800);
    _check(GeometricDistribution.of(RealScalar.of(0.1)), 2300);
    _check(HypergeometricDistribution.of(10, 40, 100), 1000); //
    _check(HypergeometricDistribution.of(10, 20, 31), 1000);
    _check(PoissonDistribution.of(RealScalar.of(2.5)), 1000);
  }

  public void testContinuous() {
    _check(ExponentialDistribution.of(RealScalar.of(2.5)), 2500); // failed with 2000, 2300
    _check(ExponentialDistribution.of(RealScalar.of(10)), 3000);
    _check(NormalDistribution.of(RealScalar.of(100), RealScalar.of(10)), 1000);
    _check(UniformDistribution.of(RealScalar.of(10), RealScalar.of(30)), 1000);
    _check(UniformDistribution.of(RealScalar.of(-100), RealScalar.of(-99)), 1000);
  }
}
