// code by gjoel
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.red.Median;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/MedianFilter.html">MedianFilter</a> */
public enum MedianFilter {
  ;
  /** Example:
   * <pre>
   * Tensor vector = Tensors.vector(0, 0, 1, 0, 0, 0, 0, 3, 3, 3, 0);
   * MedianFilter.of(vector, 1) == {0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3/2}
   * MedianFilter.of(vector, 2) == {0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3}
   * </pre>
   * 
   * @param tensor of arbitrary rank
   * @param radius non-negative integer
   * @return filtered version of input tensor with same {@link Dimensions};
   * for radius == 0 the function returns a copy of the given tensor */
  public static Tensor of(Tensor tensor, int radius) {
    if (radius < 0)
      throw new IllegalArgumentException("" + radius);
    return BlockExtract.convolve(tensor, radius, MedianFilter::flatten);
  }

  // helper function
  private static Tensor flatten(Tensor tensor) {
    return Median.of(Tensor.of(tensor.flatten(-1)));
  }
}
