// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.TensorMap;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.Expectation;

/** <p>Quote from Numerical Recipes in Fortran 77:
 * It is not uncommon, in real life, to be dealing with a distribution whose second
 * moment does not exist (i.e., is infinite). In this case, the variance or standard
 * deviation is useless as a measure of the data’s width around its central value:
 * The values obtained from equations (14.1.2) or (14.1.3) will not converge with
 * increased numbers of points, nor show any consistency from data set to data set
 * drawn from the same distribution. This can occur even when the width of the peak
 * looks, by eye, perfectly finite.
 * 
 * @see MeanDeviation
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Variance.html">Variance</a> */
public enum Variance {
  ;
  /** @param vector with at least 2 entries
   * @return variance of entries in given vector
   * @throws Exception if input is not a vector, or the input has insufficient length */
  // in Mathematica Variance[{1}] of a list of length 1 is not defined
  public static Scalar ofVector(Tensor vector) {
    Tensor mean = Mean.of(vector);
    return Norm2Squared.ofVector(TensorMap.of(scalar -> scalar.subtract(mean), vector, 1)) //
        .divide(RationalScalar.of(vector.length() - 1, 1));
  }

  /** @param distribution
   * @return variance of given probability distribution */
  public static Scalar of(Distribution distribution) {
    return Expectation.variance(distribution);
  }
}
