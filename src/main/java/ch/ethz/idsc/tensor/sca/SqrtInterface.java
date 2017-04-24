// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;

public interface SqrtInterface {
  /** @return scalar that satisfies scalar * scalar == this, or
   * @throws Exception if such an element does not exist */
  Scalar sqrt();
}
