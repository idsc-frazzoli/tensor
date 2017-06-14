// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.Scalar;

/* package */ class ContinuousPDF implements PDF {
  final ContinuousDistribution continuousDistribution;

  ContinuousPDF(ContinuousDistribution continuousDistribution) {
    this.continuousDistribution = continuousDistribution;
  }

  @Override // from PDF
  public Scalar p_equals(Scalar x) {
    return continuousDistribution.p_lessEquals(x) //
        .subtract(continuousDistribution.p_lessThan(x));
  }
}
