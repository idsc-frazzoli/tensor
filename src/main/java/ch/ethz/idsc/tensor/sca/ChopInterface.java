// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;

/** interface may be implemented by {@link Scalar}
 * to support the chop towards zero function */
public interface ChopInterface { /* optional interface */
  /** @param threshold
   * @return {@link Scalar#zero()} if Scalar has numeric precision and
   * absolute value is strictly below threshold */
  Scalar chop(double threshold);
}
