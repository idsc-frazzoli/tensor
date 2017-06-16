// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.Scalar;

/** base interface for a univariate probability distribution */
public interface Distribution extends RandomVariateInterface {
  /** Example:
   * BinomialDistribution.of(n, p).mean() == n * p
   * 
   * @return mean of distribution */
  Scalar mean();

  /** @return variance of distribution */
  Scalar variance();
}
