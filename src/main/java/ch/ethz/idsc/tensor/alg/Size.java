// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;
import java.util.Iterator;

class Size implements Iterable<MultiIndex> {
  // private static int static_numel(int[] dims) {
  // int numel = 1;
  // for (int val : dims)
  // numel *= val;
  // return numel;
  // }
  final int[] size;
  private final int[] prod;
  // final int numel;

  public Size(int... dims) {
    size = Arrays.copyOf(dims, dims.length);
    prod = new int[dims.length];
    if (0 < dims.length) {
      prod[dims.length - 1 - 0] = 1;
      for (int index : new IntRange(0, dims.length - 1))
        prod[dims.length - 1 - (index + 1)] = prod[dims.length - 1 - index] * size[dims.length - 1 - index];
    }
    // numel = static_numel(dims); // OLDTODO simplify
  }

  // @Deprecated
  // public Size drop(int index) {
  // return new Size(new MultiIndex(size).drop(index).size);
  // }
  //
  // @Deprecated
  // public Size insert(int index, int value) {
  // return new Size(new MultiIndex(size).insert(index, value).size);
  // }
  //
  public Size permute(int... sigma) {
    return new Size(new MultiIndex(size).permute(sigma).size);
  }

  public int indexOf(MultiIndex multiIndex) {
    int pos = 0;
    for (int index : new IntRange(prod.length))
      pos += prod[index] * multiIndex.at(index);
    return pos;
  }
  // public int ndims() {
  // return size.length;
  // }
  // public boolean isVectorWith(int num) {
  // return ndims() == 1 && size[0] == num;
  // }

  // public boolean isMatrix() {
  // return ndims() == 2;
  // }
  //
  // public boolean isSquareMatrix() {
  // return isMatrix() && size[0] == size[1];
  // }
  @Override
  public boolean equals(Object myObject) {
    return myObject != null //
        && myObject instanceof Size //
        && Arrays.equals(size, ((Size) myObject).size);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(size);
  }

  @Override
  public String toString() {
    StringBuilder myStringBuilder = new StringBuilder();
    myStringBuilder.append(new MultiIndex(size).toString());
    myStringBuilder.append("..");
    myStringBuilder.append(new MultiIndex(prod).toString());
    return myStringBuilder.toString();
  }

  @Override
  public Iterator<MultiIndex> iterator() {
    return new Iterator<MultiIndex>() {
      OuterProductInteger outerProductInteger = new OuterProductInteger(size, true);

      @Override
      public boolean hasNext() {
        return outerProductInteger.hasNext();
      }

      @Override
      public MultiIndex next() {
        return new MultiIndex(outerProductInteger.next());
      }
    };
  }

  public static void main(String[] args) {
    Size mySize = new Size(new int[] { 4, 2, 3 });
    System.out.println(mySize.toString());
    System.out.println(mySize.indexOf(new MultiIndex(0, 0, 0)));
    System.out.println(mySize.indexOf(new MultiIndex(3, 1, 2)));
    // OuterProductInteger myOuterProductInteger = new OuterProductInteger(new int[] { 3, 2, 4 });
    for (MultiIndex myMultiIndex : mySize) {
      System.out.println(myMultiIndex + " " + mySize.indexOf(myMultiIndex));
    }
  }
}
