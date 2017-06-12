// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.Scalar;

/* package */ class DefaultContinuousPDF implements PDF {
  private static final Random RANDOM = new Random();
  // ---
  private final ContinuousDistribution continuousDistribution;

  public DefaultContinuousPDF(ContinuousDistribution continuousDistribution) {
    this.continuousDistribution = continuousDistribution;
  }

  @Override
  public Scalar p_equals(Scalar x) {
    return continuousDistribution.p_lessEquals(x) //
        .subtract(continuousDistribution.p_lessThan(x));
  }

  @Override
  public Scalar p_lessThan(Scalar x) {
    return continuousDistribution.p_lessThan(x);
  }

  @Override
  public Scalar p_lessEquals(Scalar x) {
    return continuousDistribution.p_lessEquals(x);
  }

  @Override
  public Scalar nextSample() {
    return continuousDistribution.nextSample(RANDOM);
  }

  @Override
  public Scalar nextSample(Random random) {
    return continuousDistribution.nextSample(random);
  }

  @Override
  public Scalar mean() {
    return continuousDistribution.mean();
  }
}
