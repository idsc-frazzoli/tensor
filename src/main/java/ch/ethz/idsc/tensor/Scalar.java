// code by jph
package ch.ethz.idsc.tensor;

/** on top of the capabilities of a {@link Tensor} a scalar can be inverted
 * 
 * <p>The scalar 0 in any field is represented by {@link ZeroScalar},
 * which cannot be inverted.
 * 
 * <p>When invoking get() on {@link Scalar} the list of arguments has to be empty.
 * 
 * <p>Derived classes are immutable. */
public interface Scalar extends Tensor {
  public static final int LENGTH = -1;

  @Override // from Tensor
  Scalar add(Tensor tensor);

  @Override // from Tensor
  Scalar subtract(Tensor tensor);

  @Override // from Tensor
  Scalar multiply(Scalar scalar);

  @Override // from Tensor
  Scalar negate();

  /***************************************************/
  /** multiplicative inverse except for {@link ZeroScalar}
   * 
   * @return multiplicative inverse of this scalar */
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
   * two scalars that are equal should return the same number() // TODO test
   * 
   * @return this representation as {@link Number}
   * @throws TensorRuntimeException */
  Number number();
}
