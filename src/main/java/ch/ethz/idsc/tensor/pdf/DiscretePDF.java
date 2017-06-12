// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.IntegerQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;

/* package */ class DiscretePDF implements PDF {
  private final DiscreteDistribution discreteDistribution;

  DiscretePDF(DiscreteDistribution discreteDistribution) {
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
    while (Scalars.lessThan(RationalScalar.of(k, 1), x)) {
      cumprob = cumprob.add(discreteDistribution.p_equals(k));
      ++k;
    }
    return cumprob;
  }

  @Override // from PDF
  public Scalar p_lessEquals(Scalar x) {
    int k = discreteDistribution.lowerBound();
    Scalar cumprob = RealScalar.ZERO;
    while (Scalars.lessEquals(RationalScalar.of(k, 1), x)) {
      cumprob = cumprob.add(discreteDistribution.p_equals(k));
      ++k;
    }
    return cumprob;
  }
}
