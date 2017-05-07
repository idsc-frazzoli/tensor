// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** interface Interpolation has no direct equivalent in Mathematica */
public interface Interpolation {
  /** @param index must not be {@link Scalar}
   * @return expression similar to Tensor::get(index)
   * @throws exception if index is outside dimensions of tensor, or index is a {@link Scalar} */
  Tensor get(Tensor index);
}
