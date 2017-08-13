// code by jph
package ch.ethz.idsc.tensor;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/** reference implementation of the interface Tensor
 * parallel stream processing may be used in dot product */
/* package */ class TensorImpl implements Tensor {
  private static final String DELIMITER = ", ";
  // ---
  /* package */ final List<Tensor> list; // accessed in UnmodifiableTensor

  /* package */ TensorImpl(List<Tensor> list) {
    this.list = list;
  }

  @Override
  public Tensor unmodifiable() {
    return new UnmodifiableTensor(list);
  }

  @Override
  public Tensor copy() {
    return Tensor.of(list.stream().map(Tensor::copy));
  }

  @Override
  public Tensor get(Integer... index) {
    return get(Arrays.asList(index));
  }

  @Override
  public Scalar Get(Integer... index) {
    return (Scalar) get(Arrays.asList(index));
  }

  @Override
  public Tensor get(List<Integer> index) {
    return index.isEmpty() ? copy() : _get(index);
  }

  private Tensor _get(List<Integer> index) {
    List<Integer> sublist = index.subList(1, index.size());
    final int head = index.get(0);
    if (head == ALL)
      return Tensor.of(list.stream().map(tensor -> tensor.get(sublist)));
    return list.get(head).get(sublist);
  }

  @Override
  public void set(Tensor tensor, Integer... index) {
    _set(tensor, Arrays.asList(index));
  }

  // package visibility in order to override in unmodifiable()
  /* package */ void _set(Tensor tensor, List<Integer> index) {
    final int head = index.get(0);
    if (index.size() == 1)
      if (head == ALL) {
        TensorImpl impl = (TensorImpl) tensor;
        _range(impl).forEach(pos -> list.set(pos, impl.list.get(pos).copy()));
      } else
        list.set(head, tensor.copy());
    else {
      List<Integer> sublist = index.subList(1, index.size());
      if (head == ALL) {
        TensorImpl impl = (TensorImpl) tensor;
        _range(impl).forEach(pos -> ((TensorImpl) list.get(pos))._set(impl.list.get(pos), sublist));
      } else
        ((TensorImpl) list.get(head))._set(tensor, sublist);
    }
  }

  @Override
  public <T extends Tensor> void set(Function<T, ? extends Tensor> function, Integer... index) {
    _set(function, Arrays.asList(index));
  }

  @SuppressWarnings("unchecked")
  // package visibility in order to override in unmodifiable()
  /* package */ <T extends Tensor> void _set(Function<T, ? extends Tensor> function, List<Integer> index) {
    final int head = index.get(0);
    if (index.size() == 1)
      if (head == ALL)
        IntStream.range(0, list.size()).forEach(pos -> list.set(pos, function.apply((T) list.get(pos)).copy()));
      else
        list.set(head, function.apply((T) list.get(head)).copy());
    else {
      List<Integer> sublist = index.subList(1, index.size());
      if (head == ALL)
        list.stream().map(TensorImpl.class::cast).forEach(impl -> impl._set(function, sublist));
      else
        ((TensorImpl) list.get(head))._set(function, sublist);
    }
  }

  @Override
  public Tensor append(Tensor tensor) {
    list.add(tensor.copy());
    return this;
  }

  @Override
  public int length() {
    return list.size();
  }

  @Override
  public boolean isScalar() {
    return false;
  }

  Stream<Tensor> _flatten0() {
    return list.stream();
  }

  @Override
  public Stream<Tensor> flatten(int level) {
    if (level == 0)
      return _flatten0();
    return list.stream().flatMap(tensor -> tensor.flatten(level - 1));
  }

  @Override
  public Tensor extract(int fromIndex, int toIndex) {
    return Tensor.of(list.subList(fromIndex, toIndex).stream());
  }

  @Override
  public Tensor block(List<Integer> fromIndex, List<Integer> dimensions) {
    return fromIndex.isEmpty() ? copy() : _block(fromIndex, dimensions);
  }

  // helper function
  private Tensor _block(List<Integer> fromIndex, List<Integer> dimensions) {
    int toIndex = fromIndex.get(0) + dimensions.get(0);
    if (fromIndex.size() == 1)
      return extract(fromIndex.get(0), toIndex);
    return Tensor.of(list.subList(fromIndex.get(0), toIndex).stream() //
        .map(tensor -> ((TensorImpl) tensor)._block( //
            fromIndex.subList(1, fromIndex.size()), dimensions.subList(1, dimensions.size()))));
  }

  @Override
  public Tensor negate() {
    return Tensor.of(list.stream().map(Tensor::negate));
  }

  @Override
  public Tensor add(Tensor tensor) {
    TensorImpl impl = (TensorImpl) tensor;
    return Tensor.of(_range(impl).mapToObj(index -> list.get(index).add(impl.list.get(index))));
  }

  @Override
  public Tensor subtract(Tensor tensor) {
    // return add(tensor.negate());
    TensorImpl impl = (TensorImpl) tensor;
    return Tensor.of(_range(impl).mapToObj(index -> list.get(index).subtract(impl.list.get(index))));
  }

  @Override
  public Tensor pmul(Tensor tensor) {
    TensorImpl impl = (TensorImpl) tensor;
    return Tensor.of(_range(impl).mapToObj(index -> list.get(index).pmul(impl.list.get(index))));
  }

  @Override
  public Tensor multiply(Scalar scalar) {
    return Tensor.of(list.stream().map(tensor -> tensor.multiply(scalar)));
  }

  @Override
  public Tensor divide(Scalar scalar) {
    return Tensor.of(list.stream().map(tensor -> tensor.divide(scalar)));
  }

  /** parallel stream processing can accelerate the computation of the dot-product
   * however, for small vectors or matrices, serial computation is faster.
   * A simple heuristic should determine whether parallel processing is beneficial. */
  @Override
  public Tensor dot(Tensor tensor) {
    if (list.isEmpty() || list.get(0).isScalar()) { // quick hint whether this is a vector
      TensorImpl impl = (TensorImpl) tensor;
      return _range(impl).mapToObj(index -> impl.list.get(index).multiply((Scalar) list.get(index))) //
          .reduce(Tensor::add).orElse(RealScalar.ZERO);
    }
    return Tensor.of(list.stream().map(entry -> entry.dot(tensor)));
  }

  // helper function
  private IntStream _range(TensorImpl impl) {
    int length = list.size();
    if (length != impl.list.size()) // <- check is necessary otherwise error might be undetected
      throw TensorRuntimeException.of(this, impl); // dimensions mismatch
    return IntStream.range(0, length);
  }

  @Override
  public Tensor map(Function<Scalar, ? extends Tensor> function) {
    return Tensor.of(flatten(0).map(tensor -> tensor.map(function)));
  }

  @Override
  public Iterator<Tensor> iterator() {
    return list.iterator();
  }

  @Override // from Object
  public int hashCode() {
    return list.hashCode();
  }

  @Override // from Object
  public boolean equals(Object object) {
    if (object instanceof TensorImpl) {
      TensorImpl tensor = (TensorImpl) object;
      return list.equals(tensor.list);
    }
    return false;
  }

  @Override // from Object
  public String toString() {
    String string = list.stream().map(Tensor::toString).collect(Collectors.joining(DELIMITER));
    StringBuilder stringBuilder = new StringBuilder(2 + string.length());
    stringBuilder.append(OPENING_BRACKET);
    stringBuilder.append(string);
    stringBuilder.append(CLOSING_BRACKET);
    return stringBuilder.toString(); // "{x, y, z}"
  }

  /***************************************************/
  /* package */ int length0() {
    return list.get(0).length();
  }
}
