// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ZeroScalar;
import ch.ethz.idsc.tensor.alg.Dimensions;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Total.html">Total</a> */
public enum Total {
  ;
  /** The return value has {@link Dimensions} of input tensor reduced by 1.
   *
   * @param tensor
   * @return total sum of tensor entries at first level, or 0 if tensor is empty */
  public static Tensor of(Tensor tensor) {
    return tensor.flatten(0).parallel().reduce(Tensor::add).orElse(ZeroScalar.get());
  }

  /** The return value has {@link Dimensions} of input tensor reduced by 1.
   * 
   * <p>For instance
   * <pre>
   * prod({ 3, 4, 2 }) == 3 * 4 * 2 == 24
   * prod({ { 1, 2, 3 }, { 4, 5, 6 } }) == { 4, 10, 18 }
   * </pre>
   * 
   * <p>implementation is consistent with MATLAB::prod
   * 
   * @param tensor
   * @return total pointwise product of tensor entries at first level, or 1 if tensor is empty */
  public static Tensor prod(Tensor tensor) {
    return tensor.flatten(0).parallel().reduce(Tensor::pmul).orElse(RealScalar.ONE);
  }
}
