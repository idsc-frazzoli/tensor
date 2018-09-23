// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/** utility class for {@link Transpose} */
/* package */ class OuterProductInteger implements Iterator<List<Integer>>, Iterable<List<Integer>> {
  public static OuterProductInteger forward(Integer... dimensions) {
    return forward(Arrays.asList(dimensions));
  }

  public static OuterProductInteger forward(List<Integer> dimensions) {
    return new OuterProductInteger(dimensions.stream().mapToInt(Integer::intValue).toArray(), true);
  }

  public static OuterProductInteger of(int[] size, boolean forward) {
    return new OuterProductInteger(Arrays.copyOf(size, size.length), forward);
  }

  // ---
  private final Integer[] index;
  private final int[] size;
  private final int[] direction;
  private int count = 0;
  private final int total;

  // constructor does not copy input size, therefore use only in package
  /* package */ OuterProductInteger(int[] size, boolean forward) {
    this.size = size; // ;
    int total = 1;
    index = new Integer[size.length];
    for (int c0 = 0; c0 < size.length; ++c0) {
      index[c0] = 0;
      total *= size[c0];
    }
    this.total = total;
    direction = new int[size.length];
    for (int c0 = 0; c0 < size.length; ++c0)
      direction[c0] = forward ? size.length - c0 - 1 : c0;
  }

  @Override
  public boolean hasNext() {
    return count < total;
  }

  @Override
  public List<Integer> next() {
    if (0 < count)
      for (int c0 : direction) {
        ++index[c0];
        index[c0] %= size[c0];
        if (index[c0] != 0)
          break;
      }
    ++count;
    return Arrays.asList(index);
  }

  @Override
  public Iterator<List<Integer>> iterator() {
    return this;
  }
}
