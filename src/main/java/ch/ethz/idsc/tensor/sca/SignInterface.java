// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;

/** a {@link Scalar} implements this interface if Sign[scalar] is a meaningful operation */
public interface SignInterface {
  /** @return gives -1, 0, or 1 depending on whether this is negative, zero, or positive. */
  int signInt();
}
