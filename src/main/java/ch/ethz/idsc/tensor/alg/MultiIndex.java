// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;
import java.util.List;

class MultiIndex {
  // private
  final int[] size;

  public MultiIndex(int... dims) {
    size = Arrays.copyOf(dims, dims.length);
  }

  public MultiIndex(List<Integer> myList) {
    size = new int[myList.size()];
    int index = -1;
    for (int val : myList)
      size[++index] = val;
  }

  public int at(int index) {
    return size[index];
  }

  // public MultiIndex drop(int index) {
  // int[] dims = new int[size.length - 1];
  // for (int pos : new IntRange(index))
  // dims[pos] = size[pos];
  // for (int pos : new IntRange(index + 1, size.length))
  // dims[pos - 1] = size[pos];
  // return new MultiIndex(dims);
  // }
  //
  // public MultiIndex insert(int index, int value) {
  // int[] dims = new int[size.length + 1];
  // for (int pos : new IntRange(index))
  // dims[pos] = size[pos];
  // dims[index] = value;
  // for (int pos : new IntRange(index, size.length))
  // dims[pos + 1] = size[pos];
  // return new MultiIndex(dims);
  // }
  public MultiIndex permute(int... sigma) {
    // TODO assert that real permutation
    int[] dims = new int[size.length];
    for (int index : new IntRange(size.length))
      dims[sigma[index]] = size[index];
    return new MultiIndex(dims);
  }

  @Override
  public boolean equals(Object myObject) {
    return myObject != null //
        && myObject instanceof MultiIndex //
        && Arrays.equals(size, ((MultiIndex) myObject).size);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(size);
  }

  @Override
  public String toString() {
    StringBuilder myStringBuilder = new StringBuilder();
    myStringBuilder.append('[');
    for (int index : new IntRange(size.length)) {
      if (0 < index)
        myStringBuilder.append(',');
      myStringBuilder.append(size[index]);
    }
    myStringBuilder.append(']');
    return myStringBuilder.toString();
  }
  // public static void main(String[] args) {
  // MultiIndex myMultiIndex = new MultiIndex(2, 3, 5);
  // System.out.println(myMultiIndex);
  // System.out.println(myMultiIndex.permute(2, 0, 1));
  // System.out.println(myMultiIndex.insert(0, 0));
  // }
}
