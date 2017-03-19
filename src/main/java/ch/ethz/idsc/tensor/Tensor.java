// code by jph
package ch.ethz.idsc.tensor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.alg.Dimensions;

/** a tensor is a multi-dimensional array with the dot product */
public class Tensor implements Iterable<Tensor>, Serializable {
  /** the constant ALL is used in the function get(...)
   * to extract all elements from the respective dimension */
  public static final int ALL = -1;

  /** constructs a tensor that holds the tensors of the input stream.
   * <p>
   * for instance,
   * <ul>
   * <li>if the stream consists of {@link Scalar}s,
   * the return value represents a vector,
   * <li>if the stream consists of vectors, the return value represents a matrix.
   * </ul>
   * 
   * @param stream
   * @return tensor that holds the tensors of the input stream */
  public static Tensor of(Stream<? extends Tensor> stream) {
    return new Tensor(stream.collect(Collectors.toList()));
  }

  /** list == null for {@link Scalar} */
  private final List<Tensor> list;

  /** single constructor of Tensor with package visibility
   * 
   * @param list */
  /* package */ Tensor(List<Tensor> list) {
    this.list = list;
  }

  /** @return new immutable instance of this tensor
   * The operation doesn't duplicate data, but wraps the data container
   * with Collections::unmodifiableList and overrides setters.
   * This tensor remains modifiable. */
  public Tensor unmodifiable() {
    return new Tensor(Collections.unmodifiableList(list)) {
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

  /** duplicate mutable content of this tensor into new instance
   * 
   * @return clone of this */
  public Tensor copy() { // Scalar overrides
    return of(list.stream().map(Tensor::copy));
  }

  /** @param index
   * @return copy to this[index[0],index[1],...,All] */
  public final Tensor get(Integer... index) {
    return get(Arrays.asList(index));
  }

  /** same as function get(...) except that return type is {@link Scalar}.
   * An exception is thrown, if there is no Scalar at the specified index location.
   * 
   * @param index
   * @return {@link Scalar} at this[index[0],index[1],...] */
  public final Scalar Get(Integer... index) { // invalid for Scalar
    return (Scalar) get(Arrays.asList(index));
  }

  /** @param index
   * @return copy to this[index[0],index[1],...,All] */
  public final Tensor get(List<Integer> index) {
    return index.isEmpty() ? this : _get(index).copy();
  }

  /** @param index
   * @return reference to this[index[0],index[1],...,All] */
  private final Tensor _get(List<Integer> index) {
    List<Integer> next = index.subList(1, index.size());
    final int head = index.get(0);
    if (head == ALL)
      return of(list.stream().map(tensor -> tensor.get(next)));
    return list.get(head).get(next);
  }

  /** @param index
   * @param tensor */
  public final void set(Tensor tensor, Integer... index) { // invalid for Scalar
    _set(tensor, Arrays.asList(index));
  }

  // package visibility in order to override in unmodifiable()
  /* package */ void _set(Tensor tensor, List<Integer> index) { // invalid for Scalar
    final int head = index.get(0);
    if (index.size() == 1)
      list.set(head, tensor);
    else
      list.get(head)._set(tensor, index.subList(1, index.size()));
  }

  /** replaces element x at index with
   * 
   * <pre>
   * function.apply(x)
   * </pre>
   * 
   * set(...) allows to implement in-place operations such as
   * 
   * <pre>
   * a += 3;
   * </pre>
   * 
   * <br/>
   * function may not be invoked on an instance of {@link Scalar}.
   * 
   * @param function
   * @param index */
  public final void set(Function<Tensor, Tensor> function, Integer... index) {
    _set(function, Arrays.asList(index));
  }

  // package visibility in order to override in unmodifiable()
  /* package */ void _set(Function<Tensor, Tensor> function, List<Integer> index) {
    if (index.isEmpty())
      return;
    int head = index.get(0);
    if (index.size() == 1)
      list.set(head, function.apply(get(index.get(0))));
    else
      list.get(head)._set(function, index.subList(1, index.size()));
  }

  /** appends input tensor to this instance.
   * The length() is incremented by 1.
   * <br/>
   * the operation does not succeed for an unmodifiable instance of this.
   * 
   * @param tensor to be appended to this */
  public final void append(Tensor tensor) {
    list.add(tensor);
  }

  /** function not Mathematica compliant:
   * Length[3.14] == -1 (Mathematica evaluates Length[scalar] == 0)
   * We deviate from this to avoid the ambiguity with
   * Length[{}] == 0
   * 
   * @return length of this tensor; -1 for {@link Scalar}s */
  public int length() { // Scalar overrides final
    return list.size();
  }

  /** @param level
   * @return non-parallel stream, the user should consider invoking .parallel() */
  public Stream<Tensor> flatten(int level) { // Scalar overrides final
    if (level == 0)
      return list.stream();
    return list.stream().flatMap(tensor -> tensor.flatten(level - 1));
  }

  /** @return iteration over all tensors at level 0 in this tensor */
  @Override
  public Iterator<Tensor> iterator() {
    return list.iterator();
  }

  // function wrap does not copy data!
  // for now, function not used and hidden, because many accidental errors can happen
  /* package */ final Tensor wrap(int fromIndex, int toIndex) {
    return new Tensor(list.subList(fromIndex, toIndex));
  }

  /** @param fromIndex
   * @param toIndex
   * @return copy of sub tensor fromIndex inclusive to toIndex exclusive */
  public final Tensor extract(int fromIndex, int toIndex) {
    return of(list.subList(fromIndex, toIndex).stream());
  }

  /** @return tensor with all entries negated */
  public Tensor negate() { // Scalar overrides
    return of(list.stream().map(Tensor::negate));
  }

  /** @return tensor with all entries conjugated */
  public Tensor conjugate() { // Scalar overrides
    return of(list.stream().map(Tensor::conjugate));
  }

  /** @param tensor
   * @return this plus input tensor */
  public Tensor add(Tensor tensor) { // Scalar overrides
    return of(IntStream.range(0, list.size()).boxed() //
        .map(index -> list.get(index).add(tensor.list.get(index))));
  }

  /** @param tensor
   * @return this point-wise multiply input tensor? */
  // TODO definition!?
  // at the moment this has to be smaller or equal compared to tensor
  public Tensor pmul(Tensor tensor) { // Scalar overrides
    return of(IntStream.range(0, list.size()).boxed() //
        .map(index -> list.get(index).pmul(tensor.list.get(index))));
  }

  /** @param tensor
   * @return this minus input tensor */
  public Tensor subtract(Tensor tensor) {
    return add(tensor.negate());
  }

  /** @param scalar
   * @return tensor with elements of this tensor multiplied with scalar */
  public Tensor multiply(Scalar scalar) { // Scalar overrides
    return of(list.stream().map(entry -> entry.multiply(scalar)));
  }

  /** dot product as in Mathematica:
   * [n1,n2,n3,n4,n5] . [n5,n6,...,n9] = [n1,n2,n3,n4,n6,...,n9]
   * 
   * @param tensor
   * @return dot product between this and input tensor */
  public final Tensor dot(Tensor tensor) { // invalid for Scalar
    return _dot(Dimensions.of(this), tensor);
  }

  private Tensor _dot(List<Integer> dimensions, Tensor tensor) {
    if (1 < dimensions.size())
      return of(list.stream() //
          .parallel() // parallel because of subsequent reduce
          .map(entry -> entry._dot(dimensions.subList(1, dimensions.size()), tensor)));
    final int length = dimensions.get(0);
    if (length != tensor.length()) // <- check is necessary otherwise error might be undetected
      throw new IllegalArgumentException("dimension mismatch");
    return IntStream.range(0, length).boxed() //
        .map(index -> tensor.list.get(index).multiply((Scalar) list.get(index))) //
        .reduce(Tensor::add).orElse(ZeroScalar.get());
  }

  /** @param function
   * @return new tensor with {@link Scalar} entries replaced by
   * function evaluation of {@link Scalar} entries */
  public Tensor map(Function<Scalar, Scalar> function) { // Scalar overrides
    return of(flatten(0).map(tensor -> tensor.map(function)));
  }

  @Override // from Object
  public int hashCode() {
    return list.hashCode();
  }

  @Override // from Object
  public boolean equals(Object object) {
    // null check not required
    if (object instanceof Tensor) {
      Tensor tensor = (Tensor) object;
      return list.equals(tensor.list);
    }
    return false;
  }

  @Override // from Object
  public String toString() {
    return list.toString();
  }
}
