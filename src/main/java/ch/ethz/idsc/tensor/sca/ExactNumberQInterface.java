// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ExactNumberQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;

/** a {@link Scalar} may implement the interface to signal that the value is in exact precision.
 * For example, a {@link RationalScalar} implements the function isExactNumber() to return true.
 * 
 * <p>a {@link Scalar} that does not implement {@link ExactNumberQInterface} is assumed to
 * not represent an exact quantity by {@link ExactNumberQ}. */
public interface ExactNumberQInterface { /* optional interface */
  /** @return true, if scalar is encoded in exact precision */
  boolean isExactNumber();
}
