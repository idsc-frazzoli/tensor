// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Last.html">Last</a> */
public enum Last {
  ;
  /** @param tensor
   * @return last entry of tensor
   * @throws Exception if tensor is empty
   * @throws Exception if tensor is a {@link Scalar} */
  public static Tensor of(Tensor tensor) {
    return tensor.get(tensor.length() - 1);
  }
}
