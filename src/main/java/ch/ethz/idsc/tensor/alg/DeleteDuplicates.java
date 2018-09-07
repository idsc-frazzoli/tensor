// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/DeleteDuplicates.html">DeleteDuplicates</a> */
public enum DeleteDuplicates {
  ;
  /** Example:
   * <code>
   * DeleteDuplicates[{7, 3, 3, 7, 1, 2, 3, 2, 3, 1}] == {7, 3, 1, 2}
   * </code>
   * 
   * @param tensor
   * @return
   * @throws Exception if tensor is a {@link Scalar} */
  public static Tensor of(Tensor tensor) {
    return Tensor.of(tensor.stream().distinct().map(Tensor::copy));
  }
}
