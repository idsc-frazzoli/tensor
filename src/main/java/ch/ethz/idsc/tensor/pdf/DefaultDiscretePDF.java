// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.IntegerQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;

/* package */ class DefaultDiscretePDF implements PDF {
  private static final Random RANDOM = new Random();
  // ---
  private final DiscreteDistribution discreteDistribution;

  /* package */ DefaultDiscretePDF(DiscreteDistribution discreteDistribution) {
    this.discreteDistribution = discreteDistribution;
  }

  @Override // from PDF
  public Scalar p_equals(Scalar x) {
    if (!IntegerQ.of(x))
      return RealScalar.ZERO;
    int k = Scalars.intValueExact(x);
    return discreteDistribution.p_equals(k);
  }

  @Override // from PDF
  public Scalar p_lessThan(Scalar x) {
    int k = discreteDistribution.lowerBound();
    Scalar cumprob = RealScalar.ZERO;
    while (Scalars.lessThan(RealScalar.of(k), x)) {
      cumprob = cumprob.add(discreteDistribution.p_equals(k));
      ++k;
    }
    return cumprob;
  }

  @Override // from PDF
  public Scalar p_lessEquals(Scalar x) {
    int k = discreteDistribution.lowerBound();
    Scalar cumprob = RealScalar.ZERO;
    while (Scalars.lessEquals(RealScalar.of(k), x)) {
      cumprob = cumprob.add(discreteDistribution.p_equals(k));
      ++k;
    }
    return cumprob;
  }

  @Override // from PDF
  public Scalar nextSample() {
    return nextSample(RANDOM);
  }

  @Override // from PDF
  public Scalar nextSample(Random random) {
    Scalar reference = RealScalar.of(random.nextDouble());
    int sample = discreteDistribution.lowerBound();
    Scalar cumprob = discreteDistribution.p_equals(sample);
    while (Scalars.lessThan(cumprob, reference))
      cumprob = cumprob.add(discreteDistribution.p_equals(++sample));
    return RealScalar.of(sample);
  }

  @Override
  public Scalar mean() {
    return discreteDistribution.mean();
  }
}
