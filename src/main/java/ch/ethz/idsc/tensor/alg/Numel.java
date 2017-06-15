// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** name inspired by MATLAB
 * <a href="https://ch.mathworks.com/help/matlab/ref/numel.html">numel</a> */
public enum Numel {
  ;
  /** @param tensor
   * @return number of {@link Scalar}s in tensor */
  public static int of(Tensor tensor) {
    return (int) tensor.flatten(-1).count();
  }
}
