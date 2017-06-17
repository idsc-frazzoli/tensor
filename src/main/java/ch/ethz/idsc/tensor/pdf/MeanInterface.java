// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.Scalar;

public interface MeanInterface {
  /** Example:
   * BinomialDistribution.of(n, p).mean() == n * p
   * 
   * @return mean of distribution */
  Scalar mean();
}
