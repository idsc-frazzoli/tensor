// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.Scalar;

/** Any {@link Distribution} for which an analytic expression of the variance
 * exists should implement {@link VarianceInterface}.
 * 
 * <p>The function is used in {@link Expectation} to provide the variance of
 * a given {@link Distribution}. */
public interface VarianceInterface {
  /** @return variance of distribution */
  Scalar variance();
}
