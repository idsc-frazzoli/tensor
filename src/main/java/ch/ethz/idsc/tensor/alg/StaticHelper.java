// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.function.Function;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Tensor;

enum StaticHelper {
  ;
  /** @param tensor
   * @param function
   * @return function applied along each dimension of tensor */
  /* package */ static Tensor nestRank(Tensor tensor, Function<Tensor, Tensor> function) {
    final int rank = TensorRank.of(tensor);
    Integer[] sigma = new Integer[rank]; // [1, 2, ..., r, 0]
    IntStream.range(0, rank).forEach(i -> sigma[i] = (i + 1) % rank);
    for (int index = 0; index < rank; ++index)
      tensor = Transpose.of(function.apply(tensor), sigma);
    return tensor;
  }
}
