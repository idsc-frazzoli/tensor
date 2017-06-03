// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;

/** a {@link Scalar} may implement the interface to signal that the value is in exact precision.
 * For example, a {@link RationalScalar} implements the function isExactNumber() to return true. */
public interface ExactNumberQInterface {
  /** @return true, if scalar is encoded in exact precision */
  boolean isExactNumber();
}
