// code by jph
package ch.ethz.idsc.tensor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.alg.Dimensions;

/** implementation of tensor interface
 * parallel stream processing is used for add() and dot() */
/* package */ class TensorImpl implements Tensor {
  private final List<Tensor> list;

  /* package */ TensorImpl(List<Tensor> list) {
    this.list = list;
  }

  @Override
  public Tensor unmodifiable() {
    return new TensorImpl(Collections.unmodifiableList(list)) {
      @Override
      void _set(Tensor tensor, List<Integer> index) {
        throw new UnsupportedOperationException("unmodifiable");
      }

      @Override
      void _set(Function<Tensor, Tensor> function, List<Integer> index) {
        throw new UnsupportedOperationException("unmodifiable");
      }
    };
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
    // _get(...).copy prevents the possibility getting references to sub tensor and then modifying it...
    return index.isEmpty() ? copy() : _get(index).copy();
  }

  private Tensor _get(List<Integer> index) {
    List<Integer> next = index.subList(1, index.size());
    final int head = index.get(0);
    if (head == ALL)
      return Tensor.of(list.stream().map(tensor -> tensor.get(next)));
    return list.get(head).get(next);
  }

  @Override
  public void set(Tensor tensor, Integer... index) {
    _set(tensor, Arrays.asList(index));
  }

  // package visibility in order to override in unmodifiable()
  /* package */ void _set(Tensor tensor, List<Integer> index) {
    final int head = index.get(0);
    if (index.size() == 1)
      list.set(head, tensor);
    else
      ((TensorImpl) list.get(head))._set(tensor, index.subList(1, index.size()));
  }

  @Override
  public void set(Function<Tensor, Tensor> function, Integer... index) {
    _set(function, Arrays.asList(index));
  }

  // package visibility in order to override in unmodifiable()
  /* package */ void _set(Function<Tensor, Tensor> function, List<Integer> index) {
    if (index.isEmpty())
      return;
    int head = index.get(0);
    if (index.size() == 1)
      list.set(head, function.apply(get(head)));
    else
      ((TensorImpl) list.get(head))._set(function, index.subList(1, index.size()));
  }

  @Override
  public void append(Tensor tensor) {
    list.add(tensor.copy());
  }

  @Override
  public int length() {
    return list.size();
  }

  @Override
  public Stream<Tensor> flatten(int level) {
    if (level == 0)
      return list.stream();
    return list.stream().flatMap(tensor -> tensor.flatten(level - 1));
  }

  @Override
  public Iterator<Tensor> iterator() {
    return list.iterator();
  }

  // function wrap does not copy data!
  // for now, function not used and hidden, because many accidental errors can happen
  /* package */ Tensor wrap(int fromIndex, int toIndex) {
    return new TensorImpl(list.subList(fromIndex, toIndex));
  }

  @Override
  public Tensor extract(int fromIndex, int toIndex) {
    return Tensor.of(list.subList(fromIndex, toIndex).stream());
  }

  @Override
  public Tensor negate() {
    return Tensor.of(list.stream().map(Tensor::negate));
  }

  @Override
  public Tensor conjugate() {
    return Tensor.of(list.stream().map(Tensor::conjugate));
  }

  @Override
  public Tensor add(Tensor tensor) {
    TensorImpl impl = (TensorImpl) tensor;
    return Tensor.of(IntStream.range(0, list.size()).boxed() //
        // .parallel() //
        .map(index -> list.get(index).add(impl.list.get(index))));
  }

  @Override
  public Tensor pmul(Tensor tensor) {
    TensorImpl impl = (TensorImpl) tensor;
    return Tensor.of(IntStream.range(0, list.size()).boxed() //
        .map(index -> list.get(index).pmul(impl.list.get(index))));
  }

  @Override
  public Tensor subtract(Tensor tensor) {
    return add(tensor.negate());
  }

  @Override
  public Tensor multiply(Scalar scalar) {
    return Tensor.of(list.stream().map(entry -> entry.multiply(scalar)));
  }

  @Override
  public Tensor dot(Tensor tensor) {
    return _dot(Dimensions.of(this), (TensorImpl) tensor);
  }

  private Tensor _dot(List<Integer> dimensions, TensorImpl tensor) {
    if (1 < dimensions.size())
      return Tensor.of(list.stream() //
          .parallel() // parallel because of subsequent reduce
          .map(entry -> ((TensorImpl) entry)._dot(dimensions.subList(1, dimensions.size()), tensor)));
    final int length = dimensions.get(0);
    if (length != tensor.length()) // <- check is necessary otherwise error might be undetected
      throw new IllegalArgumentException("dimension mismatch");
    return IntStream.range(0, length).boxed() //
        .map(index -> tensor.list.get(index).multiply((Scalar) list.get(index))) //
        .reduce(Tensor::add).orElse(ZeroScalar.get());
  }

  @Override
  public Tensor map(Function<Scalar, ? extends Tensor> function) {
    return Tensor.of(flatten(0).map(tensor -> tensor.map(function)));
  }

  @Override // from Object
  public int hashCode() {
    return list.hashCode();
  }

  @Override // from Object
  public boolean equals(Object object) {
    // null check not required
    if (object instanceof TensorImpl) {
      TensorImpl tensor = (TensorImpl) object;
      return list.equals(tensor.list);
    }
    return false;
  }

  @Override // from Object
  public String toString() {
    return list.toString();
  }
}
