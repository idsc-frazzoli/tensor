// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/MeanDeviation.html">MeanDeviation</a> */
public enum MeanDeviation {
  ;
  /** @param vector with at least 1 entry
   * @return mean deviation of entries in given vector
   * @throws Exception if input is not a vector, or is empty */
  public static Scalar ofVector(Tensor vector) {
    Tensor mean = Mean.of(vector);
    return Norm1.INSTANCE.ofStream(vector.stream().map(scalar -> scalar.subtract(mean))) //
        .multiply(RationalScalar.of(1, vector.length()));
  }
}
