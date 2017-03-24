// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;

/** name inspired by MATLAB
 * <a href="https://ch.mathworks.com/help/matlab/ref/numel.html">numel</a> */
public enum Numel {
  ;
  /** @param tensor
   * @return number of scalars in tensor */
  public static int of(Tensor tensor) {
    return (int) tensor.flatten(Tensor.ALL).count();
  }
}
