// code by jph
package ch.ethz.idsc.tensor;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/** suggested base class for implementations of {@link Scalar} */
public abstract class AbstractScalar implements Scalar {
  @Override // from Scalar
  public final Scalar copy() {
    return this; // Scalar instances are immutable
  }

  @Override // from Scalar
  public final Scalar unmodifiable() {
    return this; // Scalar instances are immutable
  }

  /** when using get() on {@link AbstractScalar} the list of arguments has to be empty */
  @Override // from Scalar
  public final Scalar get(Integer... index) {
    if (0 < index.length)
      throw new IllegalArgumentException();
    return this;
  }

  /** when using Get() on {@link AbstractScalar} the list of arguments has to be empty */
  @Override // from Scalar
  public final Scalar Get(Integer... index) {
    if (0 < index.length)
      throw new IllegalArgumentException();
    return this;
  }

  /** when using get() on {@link AbstractScalar} the list of arguments has to be empty */
  @Override // from Scalar
  public final Scalar get(List<Integer> index) {
    if (0 < index.size())
      throw new IllegalArgumentException();
    return this;
  }

  @Override // from Tensor
  public final int length() {
    return LENGTH;
  }

  @Override // from Tensor
  public final Stream<Tensor> flatten(int level) {
    return Stream.of(this);
  }

  @Override // from Tensor
  public final Tensor pmul(Tensor tensor) {
    // Tensor::pmul delegates pointwise multiplication to Scalar::pmul
    return tensor.multiply(this);
  }

  @Override // from Tensor
  public final void set(Tensor tensor, Integer... index) {
    throw TensorRuntimeException.of(this);
  }

  @Override // from Tensor
  public final void set(Function<Tensor, Tensor> function, Integer... index) {
    throw TensorRuntimeException.of(this);
  }

  @Override // from Tensor
  public final void append(Tensor tensor) {
    throw TensorRuntimeException.of(this);
  }

  @Override // from Tensor
  public final Iterator<Tensor> iterator() {
    throw TensorRuntimeException.of(this);
  }

  @Override // from Tensor
  public final Tensor extract(int fromIndex, int toIndex) {
    throw TensorRuntimeException.of(this);
  }

  @Override // from Tensor
  public final Tensor dot(Tensor tensor) {
    throw TensorRuntimeException.of(this);
  }

  @Override // from Scalar
  public final Scalar add(Tensor tensor) {
    return plus((Scalar) tensor);
  }

  @Override // from Scalar
  public final Scalar subtract(Tensor tensor) {
    return add(tensor.negate());
  }

  /** @param scalar
   * @return this divided by input scalar */
  @Override // from Scalar
  public final Scalar divide(Scalar scalar) {
    return multiply(scalar.invert());
  }

  @Override // from Scalar
  public final Scalar map(Function<Scalar, Scalar> function) {
    return function.apply(this);
  }

  /***************************************************/
  @Override // from Scalar
  public Scalar abs() {
    throw TensorRuntimeException.of(this);
  }

  @Override // from Scalar
  public Scalar conjugate() {
    throw TensorRuntimeException.of(this);
  }

  /***************************************************/
  /** @param scalar
   * @return this plus input scalar */
  protected abstract Scalar plus(Scalar scalar);

  @Override // from Scalar
  public Scalar absSquared() {
    // possible default implementation:
    // Scalar abs = abs(); // <-
    // return abs.multiply(abs);
    // possible default implementation:
    // return multiply(conjugate()); // <- this may be inconsistent with implementation of abs()
    throw TensorRuntimeException.of(this);
  }

  /** classes should only override this if consistency is possible
   * for instance:
   * {@link ComplexScalar} would require two numbers, therefore
   * a single number is not implemented.
   * two scalars that are equal should return the same number()
   * 
   * @return this representation as {@link Number}
   * @throws UnsupportedOperationException */
  @Override // from Scalar
  public Number number() {
    throw TensorRuntimeException.of(this);
  }

  /***************************************************/
  @Override // from Object
  public abstract int hashCode();

  @Override // from Object
  public abstract boolean equals(Object object);

  @Override // from Object
  public abstract String toString();
}
