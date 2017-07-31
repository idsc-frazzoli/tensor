// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** name inspired by MATLAB
 * <a href="https://ch.mathworks.com/help/matlab/ref/numel.html">numel</a> */
public enum Numel {
  ;
  /** Examples:
   * Numel[3.14] == 1
   * Numel[{}] == 0
   * Numel[{4, 5, 6}] == 3
   * Numel[{{0, 0, 0}, {1, 1}}] == 5
   * 
   * @param tensor
   * @return number of {@link Scalar}s in tensor */
  public static int of(Tensor tensor) {
    return Math.toIntExact(tensor.flatten(-1).count());
  }
}
