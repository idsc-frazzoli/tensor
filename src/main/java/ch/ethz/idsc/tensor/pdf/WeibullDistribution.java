// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.Scalar;

// TODO implement
class WeibullDistribution implements ContinuousDistribution {
  public static Distribution of(Scalar alpha, Scalar beta) {
    return new WeibullDistribution(alpha, beta);
  }

  // ---
  @SuppressWarnings("unused")
  private final Scalar alpha;
  @SuppressWarnings("unused")
  private final Scalar beta;

  public WeibullDistribution(Scalar alpha, Scalar beta) {
    this.alpha = alpha;
    this.beta = beta;
  }

  @Override
  public Scalar mean() {
    return null;
  }

  @Override
  public Scalar variance() {
    return null;
  }

  @Override
  public Scalar randomVariate(Random random) {
    return null;
  }

  @Override
  public Scalar p_lessThan(Scalar x) {
    return null;
  }

  @Override
  public Scalar p_lessEquals(Scalar x) {
    return null;
  }
}
