// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;

/** interface may be implemented by {@link Scalar}
 * to support the computation of exponents */
public interface PowerInterface {
  /** @param exponent
   * @return this scalar to the power of exponent */
  Scalar power(Scalar exponent);
}
