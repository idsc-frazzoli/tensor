// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.sca.Sign;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/PositiveSemidefiniteMatrixQ.html">PositiveSemidefiniteMatrixQ</a> */
public enum PositiveSemidefiniteMatrixQ {
  ;
  /** @param tensor
   * @return true if tensor is a positive semi-definite matrix
   * @throws TensorRuntimeException if result cannot be established */
  public static boolean ofHermitian(Tensor tensor) {
    return StaticHelper.definite(tensor, Sign::isPositiveOrZero);
  }
}
