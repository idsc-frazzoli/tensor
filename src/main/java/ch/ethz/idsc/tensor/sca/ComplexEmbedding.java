// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;

/** interface defines the embedding of a {@link Scalar} in the complex plane */
public interface ComplexEmbedding {
  /** @return conjugate */
  Scalar conjugate();

  /** @return imaginary part */
  Scalar imag();

  /** @return real part */
  Scalar real();
}
