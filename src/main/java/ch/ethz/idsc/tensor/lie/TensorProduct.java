// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.TensorRank;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/TensorProduct.html">TensorProduct</a> */
public enum TensorProduct {
  ;
  /** the {@link TensorRank} of the result is the rank of {@code a} plus the rank of {@code b}
   * 
   * @param a
   * @param b
   * @return tensor product of a and b */
  public static Tensor of(Tensor a, Tensor b) {
    return a.map(b::multiply);
  }
}
