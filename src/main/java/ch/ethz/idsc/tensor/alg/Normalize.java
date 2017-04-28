// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.Norm;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Normalize.html">Normalize</a> */
public enum Normalize {
  ;
  // ---
  /** @param vector
   * @return normalized form of vector */
  public static Tensor of(Tensor vector) {
    return vector.multiply(Norm._2.of(vector).invert());
  }
}
