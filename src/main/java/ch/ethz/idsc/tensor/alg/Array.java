// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ZeroScalar;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Array.html">Array</a> */
public enum Array {
  ;
  // TODO current implementation not particularly efficient, can improve!
  /** @param function maps given index to {@link Tensor}, or {@link Scalar}
   * @param dimensions
   * @return tensor with given dimensions and entries as function(index) */
  public static Tensor of(Function<List<Integer>, ? extends Tensor> function, Integer... dimensions) {
    return of(function, Arrays.asList(dimensions));
  }

  // TODO current implementation not particularly efficient, can improve!
  /** @param function maps given index to {@link Tensor}, or {@link Scalar}
   * @param dimensions
   * @return tensor with given dimensions and entries as function(index) */
  public static Tensor of(Function<List<Integer>, ? extends Tensor> function, List<Integer> dimensions) {
    Tensor tensor = Array.zeros(dimensions);
    for (List<Integer> index : OuterProductInteger.forward(dimensions))
      tensor.set(function.apply(index), (Integer[]) index.toArray());
    return tensor;
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
}
