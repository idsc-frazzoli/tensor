// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Sort;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Median.html">Median</a> */
public enum Median {
  ;
  /** Median[{1, 2, 3, 4, 5, 6, 7}] == 4
   * Median[{1, 2, 3, 4, 5, 6, 7, 8}] == 9/2
   * 
   * @param tensor unsorted
   * @return */
  public static Tensor of(Tensor tensor) {
    Tensor sorted = Sort.of(tensor);
    int length = sorted.length();
    if (length % 2 == 0) {
      int index = length / 2;
      return Mean.of(sorted.extract(index - 1, index + 1));
    }
    return sorted.get(length / 2);
  }
}
