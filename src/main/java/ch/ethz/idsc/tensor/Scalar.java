// code by jph
package ch.ethz.idsc.tensor;

import java.util.function.Function;
import java.util.stream.Stream;

/** on top of the capabilities of a {@link Tensor}
 * a scalar can be inverted
 * <br/>
 * The scalar 0 in any field is represented by {@link ZeroScalar},
 * which cannot be inverted.
 * <br/>
 * derived classes are immutable */
public abstract class Scalar extends Tensor {
  public static final int LENGTH = -1;

  protected Scalar() {
    super(null);
  }

  @Override // from Tensor
  public final Scalar copy() {
    return this; // Scalar instances are immutable
  }

  @Override // from Tensor
  public final Scalar unmodifiable() {
    return this; // Scalar instances are immutable
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
  @Override // from Tensor
  public abstract int hashCode();

  @Override // from Tensor
  public abstract boolean equals(Object object);

  @Override // from Tensor
  public abstract String toString();
}
