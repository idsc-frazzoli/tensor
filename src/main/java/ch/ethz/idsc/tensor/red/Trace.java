// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Dimensions;

public enum Trace {
  ;
  public static Stream<Tensor> stream(Tensor tensor, int d0, int d1) {
    List<Integer> dims = Dimensions.of(tensor);
    final int length = dims.get(d0);
    if (length != dims.get(d1))
      throw new IllegalArgumentException();
    List<Integer> index = IntStream.range(0, dims.size()) //
        .map(i -> Tensor.ALL) //
        .boxed().collect(Collectors.toList());
    return IntStream.range(0, length).boxed().map(count -> {
      index.set(d0, count);
      index.set(d1, count);
      return tensor.get(index);
    });
  }

  /** @param tensor
   * @param d0
   * @param d1
   * @return trace of tensor along dimensions d0 and d1 */
  public static Tensor of(Tensor tensor, int d0, int d1) {
    return stream(tensor, d0, d1).reduce(Tensor::add).get();
  }
}
