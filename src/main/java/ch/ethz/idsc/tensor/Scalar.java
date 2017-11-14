// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.io.StringScalar;
import ch.ethz.idsc.tensor.mat.LinearSolve;

/** on top of the capabilities of a {@link Tensor} a scalar can be inverted
 * 
 * <p>The scalar 0 in any field cannot be inverted.
 * Zero is supplied by {@link #zero()}.
 * 
 * <p>When invoking {@link #get(Integer...)}, {@link #get(java.util.List)},
 * or {@link #Get(Integer...)} on {@link Scalar} the list of arguments
 * has to be empty.
 * 
 * <p>Derived classes are immutable, i.e. contents of an instance of {@link Scalar}
 * do not change during the lifetime of the instance.
 * All setter functions throw an exception when invoked on a {@link Scalar}. */
public interface Scalar extends Tensor {
  /** {@link Scalar#length()} returns LENGTH, as used in {@link Dimensions} */
  static final int LENGTH = -1;

  /** scalar addition
   * 
   * addition is commutative: a.add(b) equals b.add(a)
   * 
   * @param tensor must be {@link Scalar}
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

  /** a.divide(b) == a / b
   * 
   * <p>The default implementation is a / b == a * (b ^ -1) as
   * <code>multiply(scalar.reciprocal())</code>
   * 
   * <p>Implementations of {@link Scalar} may override the default
   * implementation due to improved numerical stability or speed,
   * for example {@link DoubleScalar}.
   * 
   * @param scalar
   * @return this divided by input scalar */
  @Override // from Tensor
  Scalar divide(Scalar scalar);

  /***************************************************/
  /** a.under(b) == b / a
   * 
   * <p>The default implementation is b / a == (a ^ -1) * b as
   * <code>reciprocal().multiply(scalar)</code>
   * 
   * <p>The application layer is discouraged from using the method.
   * The application layer should use divide instead.
   * 
   * <p>Function exists so that a scalar implementation can delegate
   * the computation of divide to the class of the input scalar:
   * <code>a.divide(b) == b.under(a)</code>
   * 
   * @param scalar
   * @return input scalar divided by this */
  Scalar under(Scalar scalar);

  /** multiplicative inverse except for {@link Scalar#zero()}
   * 
   * <p>The application layer is encouraged to use {@link #divide(Scalar)}
   * instead of the reciprocal whenever possible.
   * The use of divide typically leads to more accurate results
   * than the use of the reciprocal.
   * 
   * <p>For zero().reciprocal() there are two possible outcomes
   * 1) throw an exception, example {@link RationalScalar}
   * 2) result is encoded as "Infinity", example {@link DoubleScalar}
   * 
   * <p>The inverse is subject to numerical constraints, for instance
   * 1.0 / 4.9E-324 == Infinity
   * 
   * <p>Quote from Wikipedia: "The term reciprocal was in common use at
   * least as far back as the 3rd edition of Encyclopædia Britannica (1797)
   * to describe two numbers whose product is 1."
   * 
   * @return multiplicative inverse of this scalar
   * @throws ArithmeticException if scalar equals to 0, or cannot be inverted */
  Scalar reciprocal();

  /** absolute value
   * 
   * @return non-negative distance from zero of this
   * @throws TensorRuntimeException if absolute value is not defined
   * in the case of {@link StringScalar} for instance */
  Scalar abs();

  /** classes should override this method only if consistency is possible
   * for instance, a {@link ComplexScalar} would require two numbers:
   * the real and imaginary part, therefore a complex scalar does not
   * return a single number, but throws an exception.
   * 
   * <p>Two scalars that are equal should return two number()s that are
   * equal numerically, for instance (double)2.0 == (int)2.
   * 
   * @return this representation as {@link Number}
   * @throws TensorRuntimeException if scalar type does not support method */
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
