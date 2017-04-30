// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Tensor;

public interface Interpolation {
  /** @param index
   * @return expression similar to Tensor::get(index)
   * @throws exception if index is outside dimensions of tensor */
  Tensor get(Tensor index);
}
