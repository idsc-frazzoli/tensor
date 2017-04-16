// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.ZeroScalar;

public interface ArgInterface {
  /** @param threshold
   * @return {@link ZeroScalar} if Scalar has numeric precision and absolute value is strictly below threshold */
  Scalar arg();
}
