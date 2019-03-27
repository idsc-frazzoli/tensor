// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;

/** @see Conjugate */
@FunctionalInterface
public interface ConjugateInterface {
  /** @return conjugate of this instance */
  Scalar conjugate();
}
