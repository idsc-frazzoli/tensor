// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.TensorMap;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.Expectation;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Variance.html">Variance</a> */
public enum Variance {
  ;
  /** @param vector with length() of at least 2
   * @return scalar
   * @throws Exception if input is not a vector, or the input has insufficient length */
  // in Mathematica Variance[{1}] of a list of length 1 is not defined
  public static Scalar ofVector(Tensor vector) {
    Tensor mean = Mean.of(vector);
    return Norm2Squared.ofVector(TensorMap.of(scalar -> scalar.subtract(mean), vector, 1)) //
        .multiply(RationalScalar.of(1, vector.length() - 1));
  }

  /** @param distribution
   * @return variance of given probability distribution */
  public static Scalar of(Distribution distribution) {
    return Expectation.variance(distribution);
  }
}
