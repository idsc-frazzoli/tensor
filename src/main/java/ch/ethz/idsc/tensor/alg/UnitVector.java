// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.red.KroneckerDelta;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/UnitVector.html">UnitVector</a> */
public enum UnitVector {
  ;
  /** @param k_th
   * @param length
   * @return vector with n entries all zero, except for k-th element as 1 */
  public static Tensor of(int k_th, int length) {
    return Tensors.vector(i -> KroneckerDelta.of(i, k_th), length);
  }
}
