// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.Comparator;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ArgMax.html">ArgMax</a> */
public enum ArgMax {
  ;
  /** -1 is the Java standard, see also {@link String#indexOf(int)} */
  public static final int EMPTY = -1;

  /** @param tensor
   * @param comparator
   * @return index of maximum entry in tensor according to comparator,
   * or -1 if tensor is empty */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> int of(Tensor tensor, Comparator<T> comparator) {
    if (tensor.length() == 0)
      return EMPTY;
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

  /** Examples:
   * <pre>
   * ArgMax.of({3, 4, 2, 0, 3}) == 1
   * ArgMax.of({4, 3, 2, 4, 3}) == 0
   * </pre>
   * 
   * @param tensor
   * @return index of maximum entry in tensor, or -1 if tensor is empty */
  @SuppressWarnings("unchecked")
  public static <T extends Comparable<T>> int of(Tensor tensor) {
    if (tensor.length() == 0)
      return EMPTY;
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
