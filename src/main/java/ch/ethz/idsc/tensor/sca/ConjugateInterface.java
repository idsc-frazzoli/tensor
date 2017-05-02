// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;

/** interface may be implemented by {@link Scalar}
 * to support the computation of the complex conjugate */
public interface ConjugateInterface {
  /** @return conjugate */
  Scalar conjugate();
}
