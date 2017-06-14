// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.Scalar;

/* package */ class ContinuousCDF extends ContinuousPDF implements CDF {
  ContinuousCDF(ContinuousDistribution continuousDistribution) {
    super(continuousDistribution);
  }

  @Override // from CDF
  public Scalar p_lessThan(Scalar x) {
    return continuousDistribution.p_lessThan(x);
  }

  @Override // from CDF
  public Scalar p_lessEquals(Scalar x) {
    return continuousDistribution.p_lessEquals(x);
  }
}
