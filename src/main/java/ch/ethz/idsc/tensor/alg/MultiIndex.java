// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;
import java.util.List;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/** utility class for {@link Transpose} */
/* package */ class MultiIndex {
  static MultiIndex of(int... dims) {
    return new MultiIndex(Arrays.copyOf(dims, dims.length));
  }

  static int[] static_permute(int[] size, int[] sigma) {
    int[] dims = new int[size.length];
    for (int index = 0; index < size.length; ++index)
      dims[sigma[index]] = size[index];
    return dims;
  }

  /** @param sigma
   * @throws Exception if given sigma does not represent a permutation */
  @SuppressWarnings("unused")
  private static void _permutationQ(int... sigma) {
    Tensor test = Tensors.vectorInt(sigma);
    Tensor copy = DeleteDuplicates.of(test);
    if (!test.equals(copy))
      throw new RuntimeException();
  }

  /***************************************************/
  /** the content of size[] is not changed after construction */
  private final int[] size;

  MultiIndex(List<Integer> list) {
    size = list.stream().mapToInt(Integer::intValue).toArray();
  }

  private MultiIndex(int... dims) {
    size = dims;
  }

  public int at(int index) {
    return size[index];
  }

  /** function does not assert that sigma encodes a permutation
   * 
   * @param sigma
   * @return */
  public MultiIndex permute(int... sigma) {
    return new MultiIndex(static_permute(size, sigma));
  }

  @Override
  public String toString() {
    return Tensors.vectorInt(size).toString();
  }
}