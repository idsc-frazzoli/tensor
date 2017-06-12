// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.Scalar;

/* package */ class ContinuousPDF implements PDF {
  private final ContinuousDistribution continuousDistribution;

  ContinuousPDF(ContinuousDistribution continuousDistribution) {
    this.continuousDistribution = continuousDistribution;
  }

  @Override // from PDF
  public Scalar p_equals(Scalar x) {
    return continuousDistribution.p_lessEquals(x) //
        .subtract(continuousDistribution.p_lessThan(x));
  }

  @Override // from PDF
  public Scalar p_lessThan(Scalar x) {
    return continuousDistribution.p_lessThan(x);
  }

  @Override // from PDF
  public Scalar p_lessEquals(Scalar x) {
    return continuousDistribution.p_lessEquals(x);
  }
}
