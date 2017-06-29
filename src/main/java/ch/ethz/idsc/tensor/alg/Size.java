// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

/* package */ class Size implements Iterable<MultiIndex> {
  final int[] size;
  private final int[] prod;

  public Size(int... dims) {
    size = Arrays.copyOf(dims, dims.length);
    prod = new int[dims.length];
    if (0 < dims.length) {
      final int dmo = dims.length - 1;
      prod[dmo - 0] = 1;
      for (int index = 0; index < dmo; ++index)
        prod[dmo - (index + 1)] = prod[dmo - index] * size[dmo - index];
    }
  }

  public Size permute(int... sigma) {
    return new Size(new MultiIndex(size).permute(sigma).size);
  }

  public Size permute(Integer... sigma) {
    return permute(Stream.of(sigma).mapToInt(i -> i).toArray());
  }

  public int indexOf(MultiIndex multiIndex) {
    int pos = 0;
    for (int index = 0; index < prod.length; ++index)
      pos += prod[index] * multiIndex.at(index);
    return pos;
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

  @Override
  public boolean equals(Object object) {
    return object instanceof Size && Arrays.equals(size, ((Size) object).size);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(size);
  }

  @Override
  public String toString() {
    return new MultiIndex(size) + ".." + new MultiIndex(prod);
  }
}
