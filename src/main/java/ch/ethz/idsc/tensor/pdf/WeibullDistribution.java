// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.Scalar;

// LONGTERM implement
class WeibullDistribution implements Distribution {
  public static Distribution of(Scalar alpha, Scalar beta) {
    return new WeibullDistribution(alpha, beta);
  }

  // ---
  @SuppressWarnings("unused")
  private final Scalar alpha;
  @SuppressWarnings("unused")
  private final Scalar beta;

  private WeibullDistribution(Scalar alpha, Scalar beta) {
    this.alpha = alpha;
    this.beta = beta;
  }
}
