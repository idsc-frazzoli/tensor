// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.Scalar;

/** base interface for probability distributions */
public interface Distribution {
  /** @return mean */
  Scalar mean();

  /** @return variance */
  Scalar variance();
}
