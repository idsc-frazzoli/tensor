// code by jph
package ch.ethz.idsc.tensor.sca;

import java.math.BigDecimal;
import java.math.BigInteger;

import ch.ethz.idsc.tensor.Scalar;

/** a {@link Scalar} implements SignInterface if the signum of the scalar type
 * is a meaningful operation. The interface is used in {@link Sign}. */
public interface SignInterface {
  /** @return gives -1, 0, or 1 depending on whether this is negative, zero, or positive.
   * @see BigInteger#signum()
   * @see BigDecimal#signum() */
  int signInt();
}
