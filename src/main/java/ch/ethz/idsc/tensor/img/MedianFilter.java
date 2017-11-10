// code by gjoel
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.Median;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/MedianFilter.html">MedianFilter</a> */
public enum MedianFilter {
  ;
  /** @param tensor of arbitrary rank
   * @param radius non-negative integer
   * @return for radius == 0 the function returns a copy of the given tensor */
  public static Tensor of(Tensor tensor, int radius) {
    if (radius < 0)
      throw new IllegalArgumentException("" + radius);
    return BlockExtract.convolve(tensor, radius, MedianFilter::flatten);
  }

  // helper function
  private static Tensor flatten(Tensor block) {
    return Median.of(Tensor.of(block.flatten(-1)));
  }
}
