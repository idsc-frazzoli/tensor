// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.NavigableMap;
import java.util.TreeMap;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;

/* package */ class DiscreteCDF extends DiscretePDF implements CDF {
  // TODO build cdf in queries p_lessThan, and p_lessEquals
  private final NavigableMap<Scalar, Scalar> cdf = new TreeMap<>();

  DiscreteCDF(DiscreteDistribution discreteDistribution) {
    super(discreteDistribution);
  }

  @Override // from CDF
  public Scalar p_lessThan(Scalar x) {
    int k = discreteDistribution.lowerBound();
    Scalar cumprob = RealScalar.ZERO;
    while (Scalars.lessThan(RationalScalar.of(k, 1), x)) {
      cumprob = cumprob.add(discreteDistribution.p_equals(k));
      ++k;
    }
    return cumprob;
  }

  @Override // from CDF
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
