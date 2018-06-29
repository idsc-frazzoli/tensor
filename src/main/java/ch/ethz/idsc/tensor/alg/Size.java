// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Tensors;

/** utility class for {@link Transpose} */
/* package */ class Size implements Iterable<MultiIndex> {
  public static Size of(int... dims) {
    return new Size(Arrays.copyOf(dims, dims.length));
  }
  // ---

  private final int[] size;
  private final int[] prod;

  /** @param dims
   * @throws Exception if dims.length == 0 */
  private Size(int... dims) {
    size = dims;
    prod = new int[dims.length];
    final int dmo = dims.length - 1;
    prod[dmo] = 1;
    for (int index = 0; index < dmo; ++index)
      prod[dmo - (index + 1)] = prod[dmo - index] * size[dmo - index];
  }

  public Size permute(int... sigma) {
    return new Size(StaticHelper.static_permute(size, sigma));
  }

  public Size permute(Integer... sigma) {
    return permute(Stream.of(sigma).mapToInt(Integer::intValue).toArray());
  }

  public int indexOf(MultiIndex multiIndex) {
    int pos = 0;
    for (int index = 0; index < prod.length; ++index)
      pos += prod[index] * multiIndex.at(index);
    return pos;
  }

  public int size(int index) {
    return size[index];
  }

  @Override
  public Iterator<MultiIndex> iterator() {
    return new Iterator<MultiIndex>() {
      // invoking constructor of OuterProductInteger is verified
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
  public String toString() {
    return Tensors.vectorInt(size) + ".." + Tensors.vectorInt(prod);
  }
}
