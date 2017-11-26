// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Sqrt;

/** implementation is consistent with Mathematica::StandardDeviation
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/StandardDeviation.html">StandardDeviation</a> */
public enum StandardDeviation {
  ;
  /** @param vector
   * @return square root of variance
   * @throws Exception if input is not a vector, or the input has insufficient length */
  public static Scalar ofVector(Tensor vector) {
    return Sqrt.FUNCTION.apply(Variance.ofVector(vector));
  }
}
