// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/DeleteDuplicates.html">DeleteDuplicates</a> */
public enum DeleteDuplicates {
  ;
  /** Example:
   * DeleteDuplicates[{7, 3, 3, 7, 1, 2, 3, 2, 3, 1}] == {7, 3, 1, 2}
   * 
   * @param tensor
   * @return
   * @throws Exception if tensor is a {@link Scalar} */
  public static Tensor of(Tensor tensor) {
    if (tensor.isScalar())
      throw TensorRuntimeException.of(tensor);
    return Tensor.of(tensor.stream().distinct());
  }
}
