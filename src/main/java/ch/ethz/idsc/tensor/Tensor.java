// code by jph
package ch.ethz.idsc.tensor;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** a tensor is a multi-dimensional array with the dot product */
public interface Tensor extends Iterable<Tensor>, Serializable {
  /** the constant ALL is used in the function get(...)
   * to extract all elements from the respective dimension */
  public static final int ALL = -1;

  /** constructs a tensor that holds the tensors of the input stream.
   * <p>
   * for instance,
   * <ul>
   * <li>if the stream consists of {@link Scalar}s, the return value represents a vector,
   * <li>if the stream consists of vectors, the return value represents a matrix.
   * <li>if the stream consists of matrices, the return value represents a tensor with rank 3.
   * <li>etc.
   * </ul>
   * 
   * @param stream
   * @return tensor that holds the tensors of the input stream */
  public static Tensor of(Stream<? extends Tensor> stream) {
    return new TensorImpl(stream.collect(Collectors.toList()));
  }

  /** @return new immutable instance of this tensor
   * The operation doesn't duplicate data, but wraps the data container
   * with Collections::unmodifiableList and overrides setters.
   * This tensor remains modifiable. */
  Tensor unmodifiable();

  /** duplicate mutable content of this tensor into new instance
   * 
   * @return clone of this */
  Tensor copy();

  /** @param index
   * @return copy to this[index[0],index[1],...,All] */
  Tensor get(Integer... index);

  /** same as function get(...) except that return type is cast to {@link Scalar}.
   * An exception is thrown, if there is no Scalar at the specified index location.
   * 
   * @param index
   * @return {@link Scalar} at this[index[0],index[1],...] */
  Scalar Get(Integer... index);

  /** @param index
   * @return copy to this[index[0],index[1],...,All] */
  Tensor get(List<Integer> index);

  /** @param index
   * @param tensor */
  void set(Tensor tensor, Integer... index);

  /** replaces element x at index with
   * <code>function.apply(x)</code>
   * set(...) allows to implement in-place operations such as
   * <code>a += 3;</code>
   * <br/>
   * function may not be invoked on an instance of {@link Scalar}.
   * 
   * @param function
   * @param index */
  void set(Function<Tensor, Tensor> function, Integer... index);

  /** appends input tensor to this instance.
   * The length() is incremented by 1.
   * <br/>
   * the operation does not succeed for an unmodifiable instance of this.
   * 
   * @param tensor to be appended to this */
  void append(Tensor tensor);

  /** function <b>not</b> Mathematica compliant:
   * <code>Length[3.14] == -1</code>
   * (Mathematica evaluates <code>Length[scalar] == 0</code>)
   * <p>
   * We deviate from this to avoid the ambiguity with length of an empty list:
   * <code>Length[{}] == 0</code>
   * 
   * @return length of this tensor; -1 for {@link Scalar}s */
  int length();

  /** @param level
   * @return non-parallel stream, the user should consider invoking .parallel() */
  Stream<Tensor> flatten(int level);

  /** @param fromIndex
   * @param toIndex
   * @return copy of sub tensor fromIndex inclusive to toIndex exclusive */
  Tensor extract(int fromIndex, int toIndex);

  /** @return tensor with all entries negated */
  Tensor negate();

  /** @return tensor with all entries conjugated */
  Tensor conjugate();

  /** @param tensor
   * @return this plus input tensor */
  Tensor add(Tensor tensor);

  /** equivalent to <code>add(tensor.negate())</code>
   * 
   * @param tensor
   * @return this minus input tensor */
  Tensor subtract(Tensor tensor);

  /** @param tensor
   * @return this point-wise multiply input tensor? */
  // TODO definition!?
  // at the moment this has to be smaller or equal compared to tensor
  Tensor pmul(Tensor tensor);

  /** @param scalar
   * @return tensor with elements of this tensor multiplied with scalar */
  Tensor multiply(Scalar scalar);

  /** dot product as in Mathematica:
   * [n1,n2,n3,n4,n5] . [n5,n6,...,n9] = [n1,n2,n3,n4,n6,...,n9]
   * 
   * @param tensor
   * @return dot product between this and input tensor */
  Tensor dot(Tensor tensor);

  /** @param function
   * @return new tensor with {@link Scalar} entries replaced by
   * function evaluation of {@link Scalar} entries */
  Tensor map(Function<Scalar, Scalar> function);
}
