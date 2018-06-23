// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Tensor;

enum StaticHelper {
  ;
  /** @param tensor
   * @param operator
   * @return function applied along each dimension of tensor */
  /* package */ static Tensor nestRank(Tensor tensor, UnaryOperator<Tensor> operator) {
    final int rank = TensorRank.of(tensor);
    Integer[] sigma = new Integer[rank]; // [1, 2, ..., r, 0]
    IntStream.range(0, rank).forEach(i -> sigma[i] = (i + 1) % rank);
    for (int index = 0; index < rank; ++index)
      tensor = Transpose.of(operator.apply(tensor), sigma);
    return tensor;
  }

  static int[] static_permute(int[] size, int[] sigma) {
    int[] dims = new int[size.length];
    for (int index = 0; index < size.length; ++index)
      dims[sigma[index]] = size[index];
    return dims;
  }
}
