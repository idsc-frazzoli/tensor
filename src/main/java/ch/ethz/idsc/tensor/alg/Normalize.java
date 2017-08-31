// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.function.Function;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
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
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Normalize.html">Normalize</a> */
public enum Normalize {
  ;
  private static final Chop PRECISION = Chop._15; // magic const

  /** @param vector
   * @return normalized form of vector with respect to 2-norm */
  public static Tensor of(Tensor vector) {
    return of(vector, Norm._2);
  }

  /** @param vector
   * @return normalized vector with respect to 2-norm, or copy of vector if norm evaluates to 0 */
  public static Tensor unlessZero(Tensor vector) {
    return unlessZero(vector, Norm._2);
  }

  /** @param vector
   * @param function
   * @return result = vector*scale with positive scale such that
   * VectorNorm(result) == 1 (or numerically close to 1) */
  public static Tensor of(Tensor vector, VectorNormInterface vectorNormInterface) {
    VectorQ.orThrow(vector);
    Scalar norm = vectorNormInterface.ofVector(vector);
    int count = 0;
    while (!PRECISION.close(norm, RealScalar.ONE)) {
      vector = vector.divide(vectorNormInterface.ofVector(vector));
      norm = vectorNormInterface.ofVector(vector);
      ++count;
      if (5 < count) // in the tests the maximum observed count == 2
        throw TensorRuntimeException.of("no convergence", norm, vector);
    }
    return 0 < count ? vector : vector.copy();
  }

  /** @param vector
   * @param function
   * @return copy of vector if function(vector) == 0, else same as {@link #of(Tensor, Function)} */
  public static Tensor unlessZero(Tensor vector, VectorNormInterface vectorNormInterface) {
    if (Scalars.isZero(vectorNormInterface.ofVector(vector))) {
      VectorQ.orThrow(vector);
      return vector.copy();
    }
    return of(vector, vectorNormInterface);
  }
}
