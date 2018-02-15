// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.function.Function;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.red.VectorNormInterface;

/** Example:
 * <pre>
 * Normalize.of({2, -3, 1}, Norm._1) == {1/3, -1/2, 1/6}
 * Normalize.of({2, -3, 1}, Norm.INFINITY) == {2/3, -1, 1/3}
 * </pre>
 * 
 * <p>Normalize also works for tensors with entries of type Quantity.
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
public enum Normalize {
  ;
  /** @param vector
   * @return normalized form of vector with respect to 2-norm
   * @throws Exception if input is not a vector
   * @throws Exception if vector contains Infinity, or NaN */
  public static Tensor of(Tensor vector) {
    return of(vector, Norm._2);
  }

  /** @param vector
   * @return normalized vector with respect to 2-norm, or copy of vector if norm evaluates to 0
   * @throws Exception if input is not a vector
   * @throws Exception if vector contains Infinity, or NaN */
  public static Tensor unlessZero(Tensor vector) {
    return unlessZero(vector, Norm._2);
  }

  /** @param vector
   * @param function
   * @return result = vector*scale with positive scale such that
   * VectorNorm(result) == 1 (or numerically close to 1)
   * @throws Exception if input is not a vector
   * @throws Exception if vector contains Infinity, or NaN */
  public static Tensor of(Tensor vector, VectorNormInterface vectorNormInterface) {
    return normalize(vector, vectorNormInterface, vectorNormInterface.ofVector(vector));
  }

  /** @param vector
   * @param function
   * @return copy of vector if function(vector) == 0, else same as {@link #of(Tensor, Function)}
   * @throws Exception if input is not a vector
   * @throws Exception if vector contains Infinity, or NaN */
  public static Tensor unlessZero(Tensor vector, VectorNormInterface vectorNormInterface) {
    Scalar norm = vectorNormInterface.ofVector(vector); // throws exception if input is not a vector
    return Scalars.isZero(norm) ? vector.copy() : normalize(vector, vectorNormInterface, norm);
  }

  // helper function
  private static Tensor normalize(Tensor vector, VectorNormInterface vectorNormInterface, Scalar norm) {
    vector = vector.divide(norm); // eliminate common Unit if present
    norm = vectorNormInterface.ofVector(vector); // for verification
    Scalar error_next = norm.subtract(RealScalar.ONE).abs(); // error
    Scalar error_prev = DoubleScalar.POSITIVE_INFINITY;
    if (Scalars.nonZero(error_next))
      while (Scalars.lessThan(error_next, error_prev)) { // iteration
        vector = vector.divide(norm);
        norm = vectorNormInterface.ofVector(vector);
        error_prev = error_next;
        error_next = norm.subtract(RealScalar.ONE).abs();
      }
    return vector;
  }
}
