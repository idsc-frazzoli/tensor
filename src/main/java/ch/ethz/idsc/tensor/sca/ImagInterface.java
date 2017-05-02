// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;

/** interface may be implemented by {@link Scalar}
 * to support the extraction of the imaginary part */
public interface ImagInterface {
  /** @return imaginary part */
  Scalar imag();
}
