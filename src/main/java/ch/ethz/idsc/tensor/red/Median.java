// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Sort;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Median.html">Median</a> */
public enum Median {
  ;
  /** <code>Median[{1, 2, 3, 4, 5, 6, 7}] == 4</code>
   * <code>Median[{1, 2, 3, 4, 5, 6, 7, 8}] == 9/2</code>
   * 
   * @param tensor unsorted
   * @return */
  public static Tensor of(Tensor tensor) {
    return ofSorted(Sort.of(tensor));
  }

  /** @param sorted vector either ascending or descending
   * @return entry in the center if length is odd, otherwise the average of the two center entries */
  public static Tensor ofSorted(Tensor sorted) {
    int length = sorted.length();
    int index = length / 2;
    if (length % 2 == 0)
      return Mean.of(sorted.extract(index - 1, index + 1));
    return sorted.get(index);
  }
}
