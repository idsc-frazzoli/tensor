// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.TensorMap;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Variance.html">Variance</a> */
public enum Variance {
  ;
  /** @param vector with length() of at least 2
   * @return */
  // in Mathematica Variance[{1}] of a list of length 1 is not defined
  public static Tensor ofVector(Tensor vector) {
    Tensor mean = Mean.of(vector);
    return Norm._2Squared.of(TensorMap.of(scalar -> scalar.subtract(mean), vector, 1)) //
        .multiply(RationalScalar.of(1, vector.length() - 1));
  }
}
