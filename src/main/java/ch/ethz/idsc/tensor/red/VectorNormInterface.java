// code by jph
package ch.ethz.idsc.tensor.red;

import java.io.Serializable;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** interface defines a norm for vectors */
public interface VectorNormInterface extends Serializable {
  /** @param vector
   * @return norm of vector
   * @throws Exception if input is not a vector */
  Scalar ofVector(Tensor vector);
}
