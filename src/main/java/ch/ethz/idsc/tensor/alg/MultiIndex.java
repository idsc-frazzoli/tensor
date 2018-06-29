// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;
import java.util.List;

import ch.ethz.idsc.tensor.Tensors;

/** utility class for {@link Transpose} */
/* package */ class MultiIndex {
  static MultiIndex of(int... dims) {
    return new MultiIndex(Arrays.copyOf(dims, dims.length));
  }
  // ---

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
    return new MultiIndex(StaticHelper.static_permute(size, sigma));
  }

  @Override
  public String toString() {
    return Tensors.vectorInt(size).toString();
  }
}
