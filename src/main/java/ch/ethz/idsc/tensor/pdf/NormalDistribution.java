// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/NormalDistribution.html">NormalDistribution</a> */
public class NormalDistribution implements ContinuousDistribution {
  public static Distribution of(Scalar mean, Scalar sigma) {
    return new NormalDistribution(mean, sigma);
  }

  public static Distribution of() {
    return of(RealScalar.ZERO, RealScalar.ONE);
  }

  // ---
  private final Scalar mean;
  private final Scalar sigma;

  private NormalDistribution(Scalar mean, Scalar sigma) {
    this.mean = mean;
    this.sigma = sigma;
  }

  @Override // ContinuousDistribution
  public Scalar p_lessThan(Scalar x) {
    return null;
  }

  @Override // ContinuousDistribution
  public Scalar p_lessEquals(Scalar x) {
    return p_lessThan(x);
  }

  @Override // from RandomVariateInterface
  public Scalar randomVariate(Random random) {
    return mean.add(RealScalar.of(random.nextGaussian()).multiply(sigma));
  }

  @Override // from Distribution
  public Scalar mean() {
    return mean;
  }

  @Override // from Distribution
  public Scalar variance() {
    return sigma.multiply(sigma);
  }
}
