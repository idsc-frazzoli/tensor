// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.ZeroScalar;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Array.html">Array</a> */
public enum Array {
  ;
  /** @param function maps given index to {@link Tensor}, or {@link Scalar}
   * @param dimensions
   * @return tensor with given dimensions and entries as function(index) */
  public static Tensor of(Function<List<Integer>, ? extends Tensor> function, Integer... dimensions) {
    return of(function, Arrays.asList(dimensions));
  }

  /** @param function maps given index to {@link Tensor}, or {@link Scalar}
   * @param dimensions
   * @return tensor with given dimensions and entries as function(index) */
  public static Tensor of(Function<List<Integer>, ? extends Tensor> function, List<Integer> dimensions) {
    return _of(function, dimensions, Collections.emptyList());
  }

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

  // helper function
  private static Tensor _of(Function<List<Integer>, ? extends Tensor> function, List<Integer> dimensions, List<Integer> index) {
    int level = index.size();
    if (level == dimensions.size())
      return function.apply(index);
    Tensor tensor = Tensors.empty();
    List<Integer> copy = new ArrayList<>(index);
    copy.add(-1);
    for (int count = 0; count < dimensions.get(level); ++count) {
      copy.set(level, count);
      tensor.append(_of(function, dimensions, copy));
    }
    return tensor;
  }
}
