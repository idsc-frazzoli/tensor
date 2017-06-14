// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.Mean;
import ch.ethz.idsc.tensor.red.Variance;
import junit.framework.TestCase;

public class DistributionTest extends TestCase {
  private static void _check(Distribution distribution, int samples) {
    Tensor collect = RandomVariate.of(distribution, samples);
    Scalar mean = Mean.of(collect).Get();
    Scalar var = Variance.ofVector(collect).Get();
    Scalar tmean = distribution.mean();
    Scalar tvar = distribution.variance();
    Scalar dmean = mean.subtract(tmean).divide(tmean).abs();
    Scalar dvar = var.subtract(tvar).divide(tvar).abs();
    // System.out.println( //
    // distribution.getClass().getSimpleName() + "\t" + N.of(dmean) + "\t" + N.of(dvar));
    assertTrue(Scalars.lessThan(dmean, RealScalar.of(.2)));
    assertTrue(Scalars.lessThan(dvar, RealScalar.of(.2)));
  }

  public void testDiscrete() {
    _check(BernoulliDistribution.of(RationalScalar.of(2, 3)), 1000);
    _check(BinomialDistribution.of(10, RationalScalar.of(1, 4)), 1000);
    _check(BinomialDistribution.of(10, RationalScalar.of(7, 8)), 1000);
    _check(DiscreteUniformDistribution.of(RealScalar.of(10), RealScalar.of(30)), 1000);
    _check(GeometricDistribution.of(RealScalar.of(0.7)), 2500);
    _check(GeometricDistribution.of(RealScalar.of(0.1)), 2000);
    _check(HypergeometricDistribution.of(10, 40, 100), 1000); //
    _check(HypergeometricDistribution.of(10, 20, 31), 1000);
    _check(PoissonDistribution.of(RealScalar.of(2.5)), 1000);
  }

  public void testContinuous() {
    _check(ExponentialDistribution.of(RealScalar.of(2.5)), 2000);
    _check(ExponentialDistribution.of(RealScalar.of(10)), 2000);
    _check(NormalDistribution.of(RealScalar.of(100), RealScalar.of(10)), 1000);
    _check(UniformDistribution.of(RealScalar.of(10), RealScalar.of(30)), 1000);
    _check(UniformDistribution.of(RealScalar.of(-100), RealScalar.of(-99)), 1000);
  }
}
