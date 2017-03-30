// code by jph
package ch.ethz.idsc.tensor;

import java.util.List;
import java.util.function.Function;

/** on top of the capabilities of a {@link Tensor} a scalar can be inverted
 * 
 * <p>The scalar 0 in any field is represented by {@link ZeroScalar},
 * which cannot be inverted.
 * 
 * <p>derived classes are immutable */
public interface Scalar extends Tensor {
  public static final int LENGTH = -1;

  @Override // from Tensor
  Scalar copy();

  @Override // from Tensor
  Scalar unmodifiable();

  /** when using get() on {@link Scalar} the list of arguments has to be empty */
  @Override // from Tensor
  Scalar get(Integer... index);

  /** when using Get() on {@link Scalar} the list of arguments has to be empty */
  @Override // from Tensor
  Scalar Get(Integer... index);

  /** when using get() on {@link Scalar} the list of arguments has to be empty */
  @Override // from Tensor
  Scalar get(List<Integer> index);

  @Override // from Tensor
  Scalar add(Tensor tensor);

  @Override // from Tensor
  Scalar subtract(Tensor tensor);

  @Override // from Tensor
  Scalar multiply(Scalar scalar);

  @Override // from Tensor
  Scalar negate();

  @Override // from Tensor
  Scalar conjugate();

  @Override // from Tensor
  Scalar map(Function<Scalar, Scalar> function);

  /***************************************************/
  /** @return multiplicative inverse of this scalar */
  Scalar invert();

  /** @param scalar
   * @return this divided by input scalar */
  Scalar divide(Scalar scalar);

  /** @return distance from zero as {@link RealScalar} or
   * @throws UnsupportedOperationException */
  Scalar abs();

  /** @return |this| ^ 2 as {@link RealScalar} or
   * @throws UnsupportedOperationException */
  Scalar absSquared();

  /** classes should only override this if consistency is possible
   * for instance:
   * {@link ComplexScalar} would require two numbers, therefore
   * a single number is not implemented.
   * two scalars that are equal should return the same number()
   * 
   * @return this representation as {@link Number}
   * @throws UnsupportedOperationException */
  Number number();

  /***************************************************/
  @Override // from Object
  int hashCode();

  @Override // from Object
  boolean equals(Object object);

  @Override // from Object
  String toString();
}
