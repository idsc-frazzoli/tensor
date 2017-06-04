// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.mat.LinearSolve;

/** on top of the capabilities of a {@link Tensor} a scalar can be inverted
 * 
 * <p>The scalar 0 in any field cannot be inverted.
 * Zero is supplied by {@link Scalar#zero()}.
 * 
 * <p>When invoking get() on {@link Scalar} the list of arguments has to be empty.
 * 
 * <p>Derived classes are immutable, i.e. contents of an instance of {@link Scalar}
 * do not change during the lifetime of the instance.
 * All setter functions throw an exception when invoked on a {@link Scalar}. */
public interface Scalar extends Tensor {
  /** Scalar::length returns LENGTH */
  static final int LENGTH = -1;

  /** @param tensor must be {@link Scalar}
   * @return this plus input */
  @Override // from Tensor
  Scalar add(Tensor tensor);

  /** @param tensor must be {@link Scalar}
   * @return this minus input */
  @Override // from Tensor
  Scalar subtract(Tensor tensor);

  @Override // from Tensor
  Scalar multiply(Scalar scalar);

  @Override // from Tensor
  Scalar negate();

  /***************************************************/
  /** multiplicative inverse except for {@link Scalar#zero()}
   * 
   * @return multiplicative inverse of this scalar
   * @throws ArithmeticException if scalar equals to 0, or cannot be inverted */
  Scalar invert();

  /** implemented as
   * <code>multiply(scalar.invert())</code>
   * 
   * @param scalar
   * @return this divided by input scalar */
  Scalar divide(Scalar scalar);

  /** absolute value
   * 
   * @return typically distance from zero as {@link RealScalar},
   * generally non-negative version of this.
   * @throws TensorRuntimeException if absolute value is not defined
   * in the case of {@link StringScalar} for instance */
  Scalar abs();

  /** classes should only override this if consistency is possible
   * for instance:
   * {@link ComplexScalar} would require two numbers, therefore
   * returning a single number is not implemented.
   * 
   * <p>two scalars that are equal should return two number()s that are equal numerically.
   * 
   * @return this representation as {@link Number}
   * @throws TensorRuntimeException */
  Number number();

  /** zero() is provided for the implementation of generic functions and algorithms,
   * and used, for instance, in {@link LinearSolve}.
   * 
   * <p>zero() is not intended to provide the zero scalar in the application layer.
   * There, use for instance {@link RealScalar#ZERO}.
   * 
   * @return additive neutral element of field of this scalar
   * @see Scalars#isZero(Scalar)
   * @see Scalars#nonZero(Scalar) */
  Scalar zero();
}
