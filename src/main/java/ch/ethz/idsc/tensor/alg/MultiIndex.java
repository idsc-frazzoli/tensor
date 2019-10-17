// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.List;

import ch.ethz.idsc.tensor.Tensors;

/** utility class for {@link Transpose} */
/* package */ class MultiIndex {
  /** the content of size[] is not changed after construction */
  private final int[] size;

  private MultiIndex(int[] dims) {
    size = dims;
  }

  public MultiIndex(List<Integer> list) {
    this(list.stream().mapToInt(Integer::intValue).toArray());
  }

  public int at(int index) {
    return size[index];
  }

  /** function does not assert that sigma encodes a permutation
   * 
   * @param sigma
   * @return */
  public MultiIndex permute(int... sigma) {
    return new MultiIndex(StaticHelper.permute(size, sigma));
  }

  @Override // from Object
  public String toString() {
    return Tensors.vectorInt(size).toString();
  }
}
