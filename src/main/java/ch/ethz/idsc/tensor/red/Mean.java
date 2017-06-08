// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.Optional;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Dimensions;

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
   * In Mathematica, Mean[{}] is undefined.
   * Mean.of({}) throws an exception.
   * 
   * @param tensor non-empty
   * @return average of entries in tensor
   * @throws ArithmeticException if tensor is empty */
  public static Tensor of(Tensor tensor) {
    return Total.of(tensor).multiply(RationalScalar.of(1, tensor.length()));
  }

  /** identical to function {@link Mean#of(Tensor)}
   * except that Mean.optional({}) == Optional.empty()
   *
   * @param tensor
   * @return average of entries in tensor, or Optional.empty() if tensor is empty */
  // EXPERIMENTAL
  public static Optional<Tensor> optional(Tensor tensor) {
    return tensor.length() == 0 ? Optional.empty() : Optional.of(of(tensor));
  }
}
