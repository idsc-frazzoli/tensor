// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/PadRight.html">PadRight</a> */
public enum PadRight {
  ;
  /** Example:
   * <pre>
   * PadRight[{1, 2, 3}, 6] == {1, 2, 3, 0, 0, 0}
   * </pre>
   * 
   * @param tensor
   * @param length sequence of non-negative integers
   * @return */
  public static Tensor of(Tensor tensor, Integer... length) {
    return of(tensor, Arrays.asList(length));
  }

  /** @param tensor
   * @param list of non-negative integers
   * @return */
  public static Tensor of(Tensor tensor, List<Integer> list) {
    int dim0 = list.get(0);
    if (1 < list.size()) { // recur
      List<Integer> sublist = list.subList(1, list.size());
      if (dim0 <= tensor.length())
        return Tensor.of(tensor.stream().limit(dim0).map(entry -> of(entry, sublist)));
      List<Integer> copy = new ArrayList<>(list);
      copy.set(0, dim0 - tensor.length());
      return Join.of(Tensor.of(tensor.stream().map(entry -> of(entry, sublist))), Array.zeros(copy));
    }
    if (dim0 <= tensor.length())
      return tensor.extract(0, dim0);
    return Join.of(tensor, Array.zeros(dim0 - tensor.length()));
  }
}
