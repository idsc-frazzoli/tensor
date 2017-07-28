// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;

/** interface may be implemented by {@link Scalar}
 * to support the conversion to numeric precision */
public interface NInterface { /* optional interface */
  /** @return numerical approximation of this scalar
   * for instance 1/3 is converted to 1.0/3.0 == 0.3333... */
  Scalar n();
  // TODO also support arbitrary precision along the lines:
  // Scalar n(int digits);
}
