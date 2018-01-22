// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.Objects;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.qty.Quantity;

/** Sign gives the signum of a scalar provided by the implementation of {@link SignInterface}.
 * If the scalar type does not implement {@link SignInterface}, then an exception is thrown.
 * 
 * <p>Sign offers predicates to check positive, non-negative, negative, and non-positive scalars.
 * As checks for zero, and non-zero use {@link Scalars#isZero(Scalar)}, and {@link Scalars#nonZero(Scalar)}.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Sign.html">Sign</a> */
public enum Sign implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  private static final Scalar[] LOOKUP = { //
      RealScalar.ONE.negate(), // -1
      RealScalar.ZERO, // 0
      RealScalar.ONE }; // +1

  @Override // from ScalarUnaryOperator
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof SignInterface) {
      SignInterface signInterface = (SignInterface) scalar;
      return LOOKUP[1 + signInterface.signInt()];
    }
    throw TensorRuntimeException.of(scalar);
  }

  /** @param tensor with {@link RealScalar} entries
   * @return tensor with all scalars replaced with their sign */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }

  /** function is equivalent to
   * <code>Scalars.lessThan(scalar.zero(), scalar)</code>
   * 
   * @param scalar may be instance of {@link Quantity}
   * @return true if sign of given scalar evaluates to +1 */
  public static boolean isPositive(Scalar scalar) {
    SignInterface signInterface = (SignInterface) scalar;
    return signInterface.signInt() == +1;
  }

  /** function is equivalent to
   * <code>Scalars.lessThan(scalar, scalar.zero())</code>
   * 
   * @param scalar may be instance of {@link Quantity}
   * @return true if sign of given scalar evaluates to -1 */
  public static boolean isNegative(Scalar scalar) {
    SignInterface signInterface = (SignInterface) scalar;
    return signInterface.signInt() == -1;
  }

  /** function is equivalent to
   * <code>Scalars.lessEquals(scalar.zero(), scalar)</code>
   * 
   * @param scalar may be instance of {@link Quantity}
   * @return true if sign of given scalar evaluates to +1, or 0 */
  public static boolean isPositiveOrZero(Scalar scalar) {
    SignInterface signInterface = (SignInterface) scalar;
    return signInterface.signInt() != -1;
  }

  /** function is equivalent to
   * <code>Scalars.lessEquals(scalar, scalar.zero())</code>
   * 
   * @param scalar may be instance of {@link Quantity}
   * @return true if sign of given scalar evaluates to -1, or 0 */
  public static boolean isNegativeOrZero(Scalar scalar) {
    SignInterface signInterface = (SignInterface) scalar;
    return signInterface.signInt() != +1;
  }

  /** Remark: Functionality inspired by {@link Objects#requireNonNull(Object)}
   * 
   * @param scalar
   * @return */
  public static Scalar requirePositive(Scalar scalar) {
    if (isNegativeOrZero(scalar))
      throw TensorRuntimeException.of(scalar);
    return scalar;
  }

  /** Remark: Functionality inspired by {@link Objects#requireNonNull(Object)}
   * 
   * @param scalar
   * @return */
  public static Scalar requirePositiveOrZero(Scalar scalar) {
    if (isNegative(scalar))
      throw TensorRuntimeException.of(scalar);
    return scalar;
  }
}
