// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ZeroScalar;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Array.html">Array</a> */
public class Array {
  /** @param dimensions
   * @return tensor of {@link ZeroScalar} with given dimensions */
  public static Tensor zeros(List<Integer> dimensions) {
    if (dimensions.size() == 0)
      return ZeroScalar.get();
    return Tensor.of(IntStream.range(0, dimensions.get(0)).boxed() //
        .map(i -> zeros(dimensions.subList(1, dimensions.size()))));
  }

  /** @param dimensions
   * @return tensor of {@link ZeroScalar} with given dimensions */
  public static Tensor zeros(Integer... dimensions) {
    return zeros(Arrays.asList(dimensions));
  }
}
