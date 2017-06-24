// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Ordering.html">Ordering</a> */
public enum Ordering {
  ;
  /** @param tensor
   * @return array of indices so that tensor[i0], tensor[i1], ... is ascending */
  public static int[] of(Tensor tensor) {
    // TODO only works for tensor as vector of scalars...
    return IntStream.range(0, tensor.length()) //
        .boxed().sorted((i, j) -> Scalars.compare(tensor.Get(i), tensor.Get(j))) //
        .mapToInt(ele -> ele).toArray();
  }
}
