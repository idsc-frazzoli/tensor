// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Kurtosis.html">Kurtosis</a> */
public enum Kurtosis {
  ;
  /** @param vector
   * @return */
  public static Scalar of(Tensor vector) {
    Scalar cm2 = CentralMoment.of(vector, 2);
    return CentralMoment.of(vector, 4).divide(cm2).divide(cm2);
  }
}
