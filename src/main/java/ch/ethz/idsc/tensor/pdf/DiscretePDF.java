// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.IntegerQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;

/* package */ class DiscretePDF implements PDF {
  final DiscreteDistribution discreteDistribution;

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
}
