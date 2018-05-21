// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** multi-dimensional interpolation
 * 
 * inspired by
 * <a href="https://reference.wolfram.com/language/ref/Interpolation.html">Interpolation</a> */
public interface Interpolation {
  /** if index.length() is less than the rank r of the tensor object that is being interpolated,
   * then the function get(...) returns a tensor of rank r - index.length()
   * 
   * @param index must not be {@link Scalar}
   * @return expression similar to Tensor::get(index)
   * @throws Exception if index is outside dimensions of tensor, or index is a {@link Scalar} */
  Tensor get(Tensor index);

  /** @return {@link #get(Tensor)} cast as {@link Scalar} */
  Scalar Get(Tensor index);

  /** optimized function for interpolation along the first dimension
   * 
   * @param index
   * @return result that is identical to get(Tensors.of(index))
   * @throws Exception if index is not in the valid range */
  Tensor at(Scalar index);

  /** @return {@link #at(Scalar)} cast as {@link Scalar} */
  Scalar At(Scalar index);
}
