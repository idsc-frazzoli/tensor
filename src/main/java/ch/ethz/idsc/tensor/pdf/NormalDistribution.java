// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

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
   * @return */
  public static Distribution of(Scalar mean, Scalar sigma) {
    return new NormalDistribution(mean, sigma);
  }

  /** @return standard normal distribution with mean == 0, and standard deviation == variance == 1 */
  public static Distribution standard() {
    return STANDARD;
  }

  /** @param distribution
   * @return {@link NormalDistribution} that has the same mean and variance
   * as input distribution */
  // EXPERIMENTAL API not finalized
  public static Distribution fit(Distribution distribution) {
    return new NormalDistribution( //
        Expectation.mean(distribution), Sqrt.of(Expectation.variance(distribution)));
  }

  // ---
  private final Scalar mean;
  private final Scalar sigma;
  private final Scalar sigma_invert;

  private NormalDistribution(Scalar mean, Scalar sigma) {
    if (Scalars.lessEquals(sigma, RealScalar.ZERO))
      throw TensorRuntimeException.of(sigma);
    this.mean = mean;
    this.sigma = sigma;
    this.sigma_invert = sigma.invert();
  }

  @Override // from RandomVariateInterface
  public Scalar randomVariate(Random random) {
    return mean.add(RealScalar.of(random.nextGaussian()).multiply(sigma));
  }

  @Override // from MeanInterface
  public Scalar mean() {
    return mean;
  }

  @Override // from PDF
  public Scalar at(Scalar x) {
    return StandardNormalDistribution.INSTANCE.at( //
        x.subtract(mean).multiply(sigma_invert)).multiply(sigma_invert);
  }

  @Override // from VarianceInterface
  public Scalar variance() {
    return sigma.multiply(sigma);
  }

  @Override
  public Scalar p_lessThan(Scalar x) {
    return StandardNormalDistribution.INSTANCE.p_lessThan(x.subtract(mean).multiply(sigma_invert));
  }

  @Override
  public Scalar p_lessEquals(Scalar x) {
    return p_lessThan(x);
  }
}
