// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.Scalar;

/** Any {@link Distribution} for which an analytic expression of the mean
 * exists should implement {@link MeanInterface}.
 * 
 * <p>The function is used in {@link Expectation} to provide the mean of
 * a given {@link Distribution}. */
public interface MeanInterface {
  /** Example:
   * <pre>
   * Expectation.mean(BinomialDistribution.of(n, p)) == n * p
   * </pre>
   * 
   * @return mean of distribution */
  Scalar mean();
}
