// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;

/** interface may be implemented by {@link Scalar}
 * to support the extraction of the real part */
public interface RealInterface {
  /** @return real part */
  Scalar real();
}
