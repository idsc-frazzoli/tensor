// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/MapThread.html">MapThread</a> */
public enum MapThread {
  ;
  /** MapThread[f, List[{a1, a2, a3}, {b1, b2, b3}, ...], 1]
   * gives {f[a1, b1, ...], f[a2, b2, ...], ...}
   * 
   * Implementation consistent with Mathematica.
   * In the special case that list is empty
   * <pre>
   * MapThread[f, {}, 0] == f[]
   * MapThread[f, {}, 1] == {}
   * </pre>
   * 
   * @param function
   * @param list
   * @param level
   * @return */
  public static Tensor of(Function<List<Tensor>, ? extends Tensor> function, List<Tensor> list, int level) {
    if (0 < level) {
      long unique = list.stream().map(Tensor::length).distinct().count();
      if (1 < unique)
        throw new IllegalArgumentException();
      int length = 0 == unique ? 0 : list.get(0).length();
      return Tensor.of(IntStream.range(0, length) //
          .mapToObj(index -> of(function, extract(index, list), level - 1)));
    }
    return function.apply(list);
  }

  // helper function
  private static List<Tensor> extract(int index, List<Tensor> list) {
    return list.stream().map(tensor -> tensor.get(index)).collect(Collectors.toList());
  }
}
