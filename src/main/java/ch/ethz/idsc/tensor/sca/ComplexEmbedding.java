// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;

/** interface defines the embedding of a {@link Scalar} in the complex plane
 * 
 * @see Real
 * @see Imag */
public interface ComplexEmbedding {
  /** @return real part */
  Scalar real();

  /** @return imaginary part */
  Scalar imag();
}
