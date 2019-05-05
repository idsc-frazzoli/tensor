// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.Scalar;

/* package */ abstract class AbstractAlphaBetaDistribution extends AbstractContinuousDistribution implements //
    MeanInterface, VarianceInterface {
  static final double NEXTDOWNONE = Math.nextDown(1.0);
  // ---
  final Scalar alpha;
  final Scalar beta;

  AbstractAlphaBetaDistribution(Scalar alpha, Scalar beta) {
    this.alpha = alpha;
    this.beta = beta;
  }

  @Override // from CDF
  public final Scalar p_lessEquals(Scalar x) {
    return p_lessThan(x);
  }

  @Override // from Object
  public final String toString() {
    return String.format("%s[%s, %s]", getClass().getSimpleName(), alpha, beta);
  }
}
