// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;

/** interface may be implemented by {@link Scalar} to support
 * the computation of the exponential value in {@link Exp} */
public interface ExpInterface {
  /** @return scalar that satisfies scalar == Exp[this] */
  Scalar exp();
}
