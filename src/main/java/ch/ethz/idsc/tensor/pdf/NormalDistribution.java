// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.sca.Sqrt;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/NormalDistribution.html">NormalDistribution</a> */
public class NormalDistribution implements Distribution, //
    CDF, MeanInterface, PDF, RandomVariateInterface, VarianceInterface {
  private static final Distribution STANDARD = of(RealScalar.ZERO, RealScalar.ONE);

  /** @param mean
   * @param sigma standard deviation
   * @return instance of NormalDistribution with given characteristics */
  public static Distribution of(Scalar mean, Scalar sigma) {
    return new NormalDistribution(mean, sigma);
  }

  /** @param mean
   * @param sigma standard deviation
   * @return instance of NormalDistribution with given characteristics */
  public static Distribution of(Number mean, Number sigma) {
    return of(RealScalar.of(mean), RealScalar.of(sigma));
  }

  /** @return standard normal distribution with mean == 0, and standard deviation == variance == 1 */
  public static Distribution standard() {
    return STANDARD;
  }

  /** @param distribution
   * @return NormalDistribution that has the same mean and variance as input distribution
   * @throws Exception if mean or variance of distribution cannot be established */
  public static Distribution fit(Distribution distribution) {
    return new NormalDistribution( //
        Expectation.mean(distribution), Sqrt.of(Expectation.variance(distribution)));
  }

  // ---
  private final Scalar mean;
  private final Scalar sigma;

  private NormalDistribution(Scalar mean, Scalar sigma) {
    if (Scalars.lessEquals(sigma, RealScalar.ZERO))
      throw TensorRuntimeException.of(sigma);
    this.mean = mean;
    this.sigma = sigma;
    mean.add(sigma); // <- assert that parameters are compatible
  }

  @Override // from RandomVariateInterface
  public Scalar randomVariate(Random random) {
    return mean.add(DoubleScalar.of(random.nextGaussian()).multiply(sigma));
  }

  @Override // from MeanInterface
  public Scalar mean() {
    return mean;
  }

  @Override // from PDF
  public Scalar at(Scalar x) {
    return StandardNormalDistribution.INSTANCE.at( //
        x.subtract(mean).divide(sigma)).divide(sigma);
  }

  @Override // from VarianceInterface
  public Scalar variance() {
    return sigma.multiply(sigma);
  }

  @Override // from CDF
  public Scalar p_lessThan(Scalar x) {
    return StandardNormalDistribution.INSTANCE.p_lessThan(x.subtract(mean).divide(sigma));
  }

  @Override // from CDF
  public Scalar p_lessEquals(Scalar x) {
    return p_lessThan(x);
  }
}
