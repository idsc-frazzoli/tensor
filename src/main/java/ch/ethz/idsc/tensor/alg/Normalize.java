// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.function.Function;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.InvertUnlessZero;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Normalize.html">Normalize</a> */
public enum Normalize {
  ;
  // ---
  /** @param vector
   * @return normalized form of vector */
  public static Tensor of(Tensor vector) {
    return of(vector, Norm._2::of);
  }

  /** @param vector
   * @param function
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
   * @return vector / function(vector) */
  public static Tensor of(Tensor vector, Function<Tensor, Scalar> function) {
    return vector.multiply(function.apply(vector).invert());
  }

  /** @param vector
   * @param norm
   * @return vector of |vector|==1 subject to given norm, or zero-vector if |vector|==0 */
  public static Tensor unlessZero(Tensor vector, Function<Tensor, Scalar> function) {
    return vector.multiply(InvertUnlessZero.function.apply(function.apply(vector)));
  }
}
