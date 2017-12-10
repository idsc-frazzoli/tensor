// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Standardize.html">Standardize</a> */
// LONGTERM Standardize is defined for matrices
public enum Standardize {
  ;
  /** @param vector
   * @return result with Mean[result] == 0 and Variance[result] == 1
   * @throws Exception if input does not have sufficient elements, or is not a vector */
  public static Tensor of(Tensor vector) {
    final Tensor mean = Mean.of(vector);
    Tensor center = Tensor.of(vector.stream().map(entry -> entry.subtract(mean)));
    // StandardDeviation subtracts the mean internally
    return center.divide(StandardDeviation.ofVector(vector).Get());
  }
}
