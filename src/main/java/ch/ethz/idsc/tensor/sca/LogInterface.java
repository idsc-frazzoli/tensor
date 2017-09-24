// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;

/** interface may be implemented by {@link Scalar} to support
 * the computation of the logarithmic value in {@link Log} */
public interface LogInterface {
  /** @return scalar that satisfies scalar == Log[this] */
  Scalar log();
}
