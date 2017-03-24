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
  /** @param function
   * @param list
   * @param level
   * @return */
  // TODO test if list isEmpty
  public static Tensor of(Function<List<Tensor>, Tensor> function, List<Tensor> list, int level) {
    if (0 < level) {
      long unique = list.stream().map(Tensor::length).distinct().count();
      if (1 < unique)
        throw new IllegalArgumentException();
      return Tensor.of(IntStream.range(0, list.get(0).length()) //
          .boxed().map(index -> of(function, _extract(index, list), level - 1)));
    }
    return function.apply(list);
  }

  private static List<Tensor> _extract(int index, List<Tensor> list) {
    return list.stream().map(tensor -> tensor.get(index)).collect(Collectors.toList());
  }
}
