// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.Comparator;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ArgMin.html">ArgMin</a> */
public enum ArgMin {
  ;
  /** -1 is the Java standard, see also {@link String#indexOf(int)} */
  public static final int EMPTY = -1;

  /** @param tensor
   * @param comparator
   * @return index of minimum entry in tensor according to comparator,
   * or -1 if tensor is empty
   * @throws Exception if given tensor is a scalar */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> int of(Tensor tensor, Comparator<T> comparator) {
    if (Tensors.isEmpty(tensor))
      return EMPTY;
    Tensor ref = tensor.get(0);
    int arg = 0;
    for (int index = 1; index < tensor.length(); ++index) {
      Tensor cmp = tensor.get(index);
      if (comparator.compare((T) ref, (T) cmp) > 0) {
        ref = cmp;
        arg = index;
      }
    }
    return arg;
  }

  /** Examples:
   * <pre>
   * ArgMin.of({3, 4, 1, 2, 3}) == 2
   * ArgMin.of({1, 4, 1, 2, 3}) == 0
   * </pre>
   * 
   * @param tensor
   * @return index of minimum entry in tensor, or -1 if tensor is empty
   * @throws Exception if given tensor is a scalar */
  @SuppressWarnings("unchecked")
  public static <T extends Comparable<T>> int of(Tensor tensor) {
    if (Tensors.isEmpty(tensor))
      return EMPTY;
    T ref = (T) tensor.get(0);
    int arg = 0;
    for (int index = 1; index < tensor.length(); ++index) {
      T cmp = (T) tensor.get(index);
      if (ref.compareTo(cmp) > 0) {
        ref = cmp;
        arg = index;
      }
    }
    return arg;
  }
}
