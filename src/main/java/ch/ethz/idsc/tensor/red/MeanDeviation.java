// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/MeanDeviation.html">MeanDeviation</a> */
public enum MeanDeviation {
  ;
  /** @param vector with at least one entry
   * @return mean deviation of entries in given vector
   * @throws Exception if input is not a vector, or is empty */
  public static Scalar ofVector(Tensor vector) {
    Scalar mean = Mean.of(vector).Get();
    return Norm1.ofVector(vector.stream().map(scalar -> mean.subtract(scalar))) //
        .divide(RealScalar.of(vector.length()));
  }
}
