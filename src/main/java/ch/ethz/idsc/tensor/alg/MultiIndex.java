// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;
import java.util.List;

import ch.ethz.idsc.tensor.Tensors;

/* package */ class MultiIndex {
  // private
  final int[] size;

  public MultiIndex(int... dims) {
    size = Arrays.copyOf(dims, dims.length);
  }

  public MultiIndex(List<Integer> list) {
    size = new int[list.size()];
    int index = -1;
    for (int val : list)
      size[++index] = val;
  }

  public int at(int index) {
    return size[index];
  }

  public MultiIndex permute(int... sigma) {
    // TODO assert that real permutation
    int[] dims = new int[size.length];
    for (int index = 0; index < size.length; ++index)
      dims[sigma[index]] = size[index];
    return new MultiIndex(dims);
  }

  @Override
  public boolean equals(Object object) {
    return object instanceof MultiIndex && Arrays.equals(size, ((MultiIndex) object).size);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(size);
  }

  @Override
  public String toString() {
    return Tensors.vectorInt(size).toString();
  }
}
