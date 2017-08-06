// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Standardize.html">Standardize</a> */
enum Standardize {
  ;
  /** @param vector
   * @return result with Mean[result] == 0 and Variance[result] == 1 */
  public static Tensor of(Tensor vector) {
    final Tensor mean = Mean.of(vector);
    Tensor center = Tensor.of(vector.flatten(0).map(entry -> entry.subtract(mean)));
    // StandardDeviation subtracts the mean internally
    return center.divide(StandardDeviation.ofVector(vector).Get());
  }
}
