// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.sca.Power;

/** implementation is consistent with Mathematica::GeometricMean
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/GeometricMean.html">GeometricMean</a> */
public enum GeometricMean {
  ;
  /** computes the geometric mean of the entries on the first level of given tensor.
   * The return value has {@link Dimensions} of input tensor reduced by 1.
   * 
   * <p>For instance,
   * <ul>
   * <li>for a vector of scalars, the geometric mean is a {@link Scalar}
   * <li>for a matrix, the function returns a the geometric mean of rows as a vector
   * </ul>
   * 
   * <pre>
   * GeometricMean[{4, 9}] == (4 * 9)^1/2 == 6
   * GeometricMean[{{5, 10}, {2, 1}, {4, 3}, {12, 15}}] == {2 30^(1/4), 2^(1/4) Sqrt[15]}
   * </pre>
   * 
   * @param tensor non-empty
   * @return average of entries in tensor
   * @throws ArithmeticException if tensor is empty */
  public static Tensor of(Tensor tensor) {
    return Total.prod(tensor).map(Power.function(RationalScalar.of(1, tensor.length())));
  }
}
