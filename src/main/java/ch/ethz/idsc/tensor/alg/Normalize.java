// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.function.Function;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.red.Norm;
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
    return of(vector, Norm._2::of);
  }

  /** @param vector
   * @param norm
   * @return vector of |vector|==1 subject to given norm
   * @throws ArithmeticException if |vector|==0 */
  public static Tensor of(Tensor vector, Norm norm) {
    return of(vector, norm::of);
  }

  /** @param vector
   * @param norm
   * @return vector of |vector|==1 subject to given norm, or zero-vector if |vector|==0 */
  public static Tensor unlessZero(Tensor vector, Norm norm) {
    return unlessZero(vector, norm::of);
  }

  /** @param vector
   * @param function
   * @return result = vector*scale with positive scale such that function(result) == 1 (or numerically close to 1) */
  public static Tensor of(Tensor vector, Function<Tensor, Scalar> function) {
    VectorQ.orThrow(vector);
    Scalar norm = function.apply(vector);
    int count = 0;
    while (!PRECISION.close(norm, RealScalar.ONE)) {
      vector = vector.divide(function.apply(vector));
      norm = function.apply(vector);
      ++count;
      if (5 < count) // in the tests the maximum observed count == 2
        throw TensorRuntimeException.of("no convergence", norm, vector);
    }
    return 0 < count ? vector : vector.copy();
  }

  /** @param vector
   * @param function
   * @return copy of vector if function(vector) == 0, else same as {@link #of(Tensor, Function)} */
  public static Tensor unlessZero(Tensor vector, Function<Tensor, Scalar> function) {
    if (Scalars.isZero(function.apply(vector))) {
      VectorQ.orThrow(vector);
      return vector.copy();
    }
    return of(vector, function);
  }
}
