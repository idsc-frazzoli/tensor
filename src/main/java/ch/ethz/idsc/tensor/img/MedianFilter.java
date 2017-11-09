// code by gjoel
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.VectorQ;
import ch.ethz.idsc.tensor.red.Median;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/MedianFilter.html">MedianFilter</a> */
public enum MedianFilter {
  ;
  /** @param tensor
   * @param radius
   * @return */
  public static Tensor of(Tensor tensor, int radius) {
    VectorQ.elseThrow(tensor); // TODO does not yet work for non-vectors
    return StaticHelper.filter(tensor, radius, Median::of);
  }
}
