// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/TensorProduct.html">TensorProduct</a> */
public enum TensorProduct {
  ;
  /** @param a
   * @param b
   * @return */
  public static Tensor of(Tensor a, Tensor b) {
    return a.map(b::multiply);
  }
}
