// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.function.Function;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.red.VectorNormInterface;
import ch.ethz.idsc.tensor.sca.Chop;

/** norms of resulting vectors may deviate from 1 numerically.
 * Observed deviations are
 * <pre>
 * 0.9999999999999998
 * 1.0000000000000004
 * 1.0000000000000009
 * </pre>
 * 
 * <p>Normalize also works for tensors with entries of type Quantity.
 * The computation is consistent with Mathematica:
 * Normalize[{Quantity[3, "Meters"], Quantity[4, "Meters"]}] == {3/5, 4/5}
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Normalize.html">Normalize</a> */
public enum Normalize {
  ;
  private static final Chop PRECISION = Chop._15; // magic const
  /** in the tests the maximum iteration count == 2 */
  private static final int MAX_ITERATIONS = 5;

  /** @param vector
   * @return normalized form of vector with respect to 2-norm
   * @throws Exception if input is not a vector */
  public static Tensor of(Tensor vector) {
    return of(vector, Norm._2);
  }

  /** @param vector
   * @return normalized vector with respect to 2-norm, or copy of vector if norm evaluates to 0
   * @throws Exception if input is not a vector */
  public static Tensor unlessZero(Tensor vector) {
    return unlessZero(vector, Norm._2);
  }

  /** @param vector
   * @param function
   * @return result = vector*scale with positive scale such that
   * VectorNorm(result) == 1 (or numerically close to 1)
   * @throws Exception if input is not a vector */
  public static Tensor of(Tensor vector, VectorNormInterface vectorNormInterface) {
    return _normalize(vector, vectorNormInterface, vectorNormInterface.ofVector(vector));
  }

  /** @param vector
   * @param function
   * @return copy of vector if function(vector) == 0, else same as {@link #of(Tensor, Function)}
   * @throws Exception if input is not a vector */
  public static Tensor unlessZero(Tensor vector, VectorNormInterface vectorNormInterface) {
    Scalar norm = vectorNormInterface.ofVector(vector); // throws exception if input is not a vector
    return Scalars.isZero(norm) ? vector.copy() : _normalize(vector, vectorNormInterface, norm);
  }

  // helper function
  private static Tensor _normalize(Tensor vector, VectorNormInterface vectorNormInterface, Scalar norm) {
    if (norm instanceof RealScalar && PRECISION.close(norm, RealScalar.ONE))
      return vector.copy();
    for (int count = 0; count < MAX_ITERATIONS; ++count) {
      vector = vector.divide(norm);
      norm = vectorNormInterface.ofVector(vector);
      if (PRECISION.close(norm, RealScalar.ONE))
        return vector;
    }
    // desired precision not reached, but
    // result is expected to have norm close to 1
    return vector; // case not covered by tests
  }
}
