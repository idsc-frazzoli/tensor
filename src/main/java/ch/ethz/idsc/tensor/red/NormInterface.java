// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** interface defines a norm for vectors and matrices */
public interface NormInterface extends VectorNormInterface {
  /** @param matrix
   * @return norm of matrix
   * @throws Exception if input is not a matrix */
  Scalar ofMatrix(Tensor matrix);
}
