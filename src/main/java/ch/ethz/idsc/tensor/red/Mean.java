// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.Expectation;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Mean.html">Mean</a> */
public enum Mean {
  ;
  /** computes the mean of the entries on the first level of given tensor.
   * The return value has {@link Dimensions} of input tensor reduced by 1.
   * 
   * <p>For instance,
   * <ul>
   * <li>for a vector of scalars, the mean is a {@link Scalar}
   * <li>for a matrix, the function returns a the average of rows as a vector
   * </ul>
   * 
   * <p>Careful: Mean.of({}) throws an exception.
   * In Mathematica, Mean[{}] is undefined.
   * 
   * @param tensor non-empty
   * @return average of entries in tensor
   * @throws ArithmeticException if tensor is empty
   * @throws TensorRuntimeException if tensor is a {@link Scalar} */
  public static Tensor of(Tensor tensor) {
    return Total.of(tensor).divide(RationalScalar.of(tensor.length(), 1));
  }

  /** @param distribution
   * @return mean of given probability distribution */
  public static Scalar of(Distribution distribution) {
    return Expectation.mean(distribution);
  }
}
