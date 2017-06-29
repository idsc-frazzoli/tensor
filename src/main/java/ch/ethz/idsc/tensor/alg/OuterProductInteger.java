// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

/* package */ class OuterProductInteger implements Iterator<List<Integer>>, Iterable<List<Integer>> {
  final Integer[] myInteger;
  final int[] myInt;
  final int[] direction;
  int count = 0;
  final int total;

  public static OuterProductInteger forward(Integer... dimensions) {
    return forward(Arrays.asList(dimensions));
  }

  public static OuterProductInteger forward(List<Integer> dimensions) {
    int[] ints = IntStream.range(0, dimensions.size()).map(i -> dimensions.get(i)).toArray();
    return new OuterProductInteger(ints, true);
  }

  // TODO not final class design
  public OuterProductInteger(int[] myInt, boolean forward) {
    int total = 1;
    myInteger = new Integer[myInt.length];
    for (int c0 = 0; c0 < myInt.length; ++c0) {
      myInteger[c0] = 0;
      total *= myInt[c0];
    }
    this.myInt = myInt;
    this.total = total;
    direction = new int[myInt.length];
    for (int c0 = 0; c0 < myInt.length; ++c0)
      direction[c0] = forward ? myInt.length - c0 - 1 : c0;
  }

  // @Deprecated // because rather use explicit
  public OuterProductInteger(int[] myInt) {
    this(myInt, false);
  }

  @Override
  public boolean hasNext() {
    return count < total;
  }

  @Override
  public List<Integer> next() {
    if (0 < count)
      // for (int c0 = myInt.length - 1; 0 <= c0; --c0) {
      for (int c0 : direction) {
        ++myInteger[c0];
        myInteger[c0] %= myInt[c0];
        if (myInteger[c0] != 0)
          break;
      }
    ++count;
    return Arrays.asList(myInteger);
  }

  @Override
  public Iterator<List<Integer>> iterator() {
    return this;
  }
}
