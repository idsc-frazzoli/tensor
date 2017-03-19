// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;
import java.util.List;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Join.html">Join</a> */
public class Join {
  /** @param tensors
   * @return joins elements of all tensors along their first dimension */
  public static Tensor of(Tensor... tensors) {
    return of(Arrays.asList(tensors));
  }

  /** @param list
   * @return tensors in the list along their first dimension */
  public static Tensor of(List<Tensor> list) {
    return Tensor.of(list.stream().flatMap(tensor -> tensor.flatten(0)));
  }

  /** @param level
   * @param tensors
   * @return joins tensors along dimension level */
  public static Tensor of(int level, Tensor... tensors) {
    return of(level, Arrays.asList(tensors));
  }

  /** @param level
   * @param list
   * @return joins tensors in the list along dimension level */
  public static Tensor of(int level, List<Tensor> list) {
    return MapThread.of(Join::of, list, level);
  }
}
