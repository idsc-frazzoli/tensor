// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.opt.TensorScalarFunction;
import ch.ethz.idsc.tensor.opt.TensorUnaryOperator;
import ch.ethz.idsc.tensor.red.Norm;

/** Normalize also works for tensors with entries of type Quantity.
 * The computation is consistent with Mathematica:
 * Normalize[{Quantity[3, "Meters"], Quantity[4, "Meters"]}] == {3/5, 4/5}
 * 
 * <p>For {@link Norm#INFINITY} the norm of the normalized vector evaluates
 * to the exact value 1.0.
 * In general, the norms of resulting vectors may deviate from 1.0 numerically.
 * The deviations depend on the type of norm.
 * Tests for vectors with 1000 normal distributed random entries exhibit
 * <pre>
 * {@link Norm#_1} min = 0.9999999999999987; max = 1.0000000000000018
 * {@link Norm#_2} min = 0.9999999999999996; max = 1.0000000000000004
 * </pre>
 * 
 * <p>The implementation divides a given vector by the norm until the
 * iteration stops improving. The implementation is parameter free.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Normalize.html">Normalize</a> */
public class Normalize implements TensorUnaryOperator {
  /** Examples:
   * <pre>
   * Normalize.with(Norm._1).apply({2, -3, 1}) == {1/3, -1/2, 1/6}
   * Normalize.with(Norm.INFINITY).apply({2, -3, 1}) == {2/3, -1, 1/3}
   * </pre>
   * 
   * @param norm
   * @return operator that normalizes a vector using the given norm */
  public static TensorUnaryOperator with(Norm norm) {
    return new Normalize(norm::ofVector);
  }

  /** Examples:
   * <pre>
   * Normalize.with(v -> Total.of(v).Get())
   * </pre>
   * 
   * @param tensorScalarFunction
   * @return operator that normalizes a vector using the given tensorScalarFunction */
  public static TensorUnaryOperator with(TensorScalarFunction tensorScalarFunction) {
    return new Normalize(tensorScalarFunction);
  }

  // ---
  /* package */ final TensorScalarFunction tensorScalarFunction;

  /* package */ Normalize(TensorScalarFunction tensorScalarFunction) {
    this.tensorScalarFunction = tensorScalarFunction;
  }

  @Override
  public Tensor apply(Tensor vector) { /* non-final */
    return normalize(vector, tensorScalarFunction.apply(vector));
  }

  /** @param vector
   * @param scalar equals to tensorScalarFunction.apply(vector)
   * @return */
  /* package */ final Tensor normalize(Tensor vector, Scalar scalar) {
    vector = vector.divide(scalar); // eliminate common Unit if present
    scalar = tensorScalarFunction.apply(vector); // for verification
    Scalar error_next = scalar.subtract(RealScalar.ONE).abs(); // error
    Scalar error_prev = DoubleScalar.POSITIVE_INFINITY;
    if (Scalars.nonZero(error_next))
      while (Scalars.lessThan(error_next, error_prev)) { // iteration
        vector = vector.divide(scalar);
        scalar = tensorScalarFunction.apply(vector);
        error_prev = error_next;
        error_next = scalar.subtract(RealScalar.ONE).abs();
      }
    return vector;
  }
}
