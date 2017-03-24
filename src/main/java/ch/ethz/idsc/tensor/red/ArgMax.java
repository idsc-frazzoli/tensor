// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.Comparator;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ArgMax.html">ArgMax</a> */
public enum ArgMax {
  ;
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> int of(Tensor tensor, Comparator<T> comparator) {
    if (tensor.length() == 0)
      return -1;
    Tensor ref = tensor.get(0);
    int arg = 0;
    for (int index = 1; index < tensor.length(); ++index) {
      Tensor cmp = tensor.get(index);
      if (comparator.compare((T) ref, (T) cmp) < 0) {
        ref = cmp;
        arg = index;
      }
    }
    return arg;
  }

  /** @param tensor
   * @return index of maximum entry in tensor */
  @SuppressWarnings("unchecked")
  public static <T extends Comparable<T>> int of(Tensor tensor) {
    if (tensor.length() == 0)
      return -1;
    T max = (T) tensor.get(0);
    int arg = 0;
    for (int index = 1; index < tensor.length(); ++index) {
      T cmp = (T) tensor.get(index);
      if (max.compareTo(cmp) < 0) {
        max = cmp;
        arg = index;
      }
    }
    return arg;
  }
}
