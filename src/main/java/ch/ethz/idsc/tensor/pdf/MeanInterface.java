// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.Scalar;

/* package until API finalized */ interface MeanInterface {
  /** Example:
   * BinomialDistribution.of(n, p).mean() == n * p
   * 
   * @return mean of distribution */
  Scalar mean();
}
