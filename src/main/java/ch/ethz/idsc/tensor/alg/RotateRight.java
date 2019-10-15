// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/RotateRight.html">RotateRight</a>
 * 
 * @see RotateLeft */
public enum RotateRight {
  ;
  /** RotateRight[{a, b, c, d, e}, 2] == {d, e, a, b, c}
   * 
   * @param tensor
   * @param n any integer
   * @return
   * @throws Exception if given tensor is a {@link Scalar} */
  public static Tensor of(Tensor tensor, int n) {
    return RotateLeft.of(tensor, -n);
  }
}
