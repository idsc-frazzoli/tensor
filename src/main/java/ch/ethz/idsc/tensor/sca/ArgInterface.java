// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;

/** interface may be implemented by {@link Scalar}
 * to support the computation of the complex argument in {@link Arg} */
public interface ArgInterface {
  /** @return argument of this number in the complex plane */
  Scalar arg();
}
