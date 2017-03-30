// code by jph
package ch.ethz.idsc.tensor;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/** on top of the capabilities of a {@link Tensor} a scalar can be inverted
 * 
 * <p>The scalar 0 in any field is represented by {@link ZeroScalar},
 * which cannot be inverted.
 * 
 * <p>derived classes are immutable */
public abstract class Scalar implements Tensor {
  public static final int LENGTH = -1;

  @Override // from Tensor
  public final Scalar copy() {
    return this; // Scalar instances are immutable
  }

  @Override // from Tensor
  public final Scalar unmodifiable() {
    return this; // Scalar instances are immutable
  }

  /** when using get() on {@link Scalar} the list of arguments has to be empty */
  @Override
  public final Scalar get(Integer... index) {
    if (0 < index.length)
      throw new IllegalArgumentException();
    return this;
  }

  /** when using Get() on {@link Scalar} the list of arguments has to be empty */
  @Override
  public final Scalar Get(Integer... index) {
    if (0 < index.length)
      throw new IllegalArgumentException();
    return this;
  }

  /** when using get() on {@link Scalar} the list of arguments has to be empty */
  @Override
  public final Tensor get(List<Integer> index) {
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

  /***************************************************/
  @Override // from Tensor
  public final Scalar add(Tensor tensor) {
    return plus((Scalar) tensor);
  }

  /** @param scalar
   * @return this plus input scalar */
  protected abstract Scalar plus(Scalar scalar);

  @Override // from Tensor
  public abstract Scalar multiply(Scalar scalar);

  @Override // from Tensor
  public final Tensor pmul(Tensor tensor) {
    // Tensor::pmul delegates pointwise multiplication to Scalar::pmul
    return tensor.multiply(this);
  }

  /***************************************************/
  @Override // from Tensor
  public abstract Scalar negate();

  /** @return multiplicative inverse of this scalar */
  public abstract Scalar invert();

  /** @return distance from zero as {@link RealScalar} or
   * @throws UnsupportedOperationException */
  public Scalar abs() {
    throw new UnsupportedOperationException("abs");
  }

  @Override // from Tensor
  public Scalar conjugate() {
    throw new UnsupportedOperationException("conjugate");
  }

  /** @return |this| ^ 2 as {@link RealScalar} or
   * @throws UnsupportedOperationException */
  public Scalar absSquared() {
    // possible default implementation:
    // Scalar abs = abs(); // <-
    // return abs.multiply(abs);
    // possible default implementation:
    // return multiply(conjugate()); // <- this may be inconsistent with implementation of abs()
    throw new UnsupportedOperationException("absSqared");
  }

  /** classes should only override this if consistency is possible
   * for instance:
   * {@link ComplexScalar} would require two numbers, therefore
   * a single number is not implemented.
   * two scalars that are equal should return the same number()
   * 
   * @return this representation as {@link Number}
   * @throws UnsupportedOperationException */
  public Number number() {
    throw new UnsupportedOperationException("number " + getClass());
  }

  /***************************************************/
  @Override // from Tensor
  public final Scalar subtract(Tensor tensor) {
    return add(tensor.negate());
  }

  /** @param scalar
   * @return this divided by input scalar */
  public final Scalar divide(Scalar scalar) {
    return multiply(scalar.invert());
  }

  @Override // from Tensor
  public final Scalar map(Function<Scalar, Scalar> function) {
    return function.apply(this);
  }

  /***************************************************/
  @Override // from Object
  public abstract int hashCode();

  @Override // from Object
  public abstract boolean equals(Object object);

  @Override // from Object
  public abstract String toString();

  /***************************************************/
  @Override
  public final void set(Tensor tensor, Integer... index) {
    throw new UnsupportedOperationException("set " + getClass());
  }

  @Override
  public final void set(Function<Tensor, Tensor> function, Integer... index) {
    throw new UnsupportedOperationException("set " + getClass());
  }

  @Override
  public final void append(Tensor tensor) {
    throw new UnsupportedOperationException("append " + getClass());
  }

  @Override
  public final Iterator<Tensor> iterator() {
    throw new UnsupportedOperationException("iterator " + getClass());
  }

  @Override
  public final Tensor extract(int fromIndex, int toIndex) {
    throw new UnsupportedOperationException("extract " + getClass());
  }

  @Override
  public final Tensor dot(Tensor tensor) {
    throw new UnsupportedOperationException("dot " + getClass());
  }
}
