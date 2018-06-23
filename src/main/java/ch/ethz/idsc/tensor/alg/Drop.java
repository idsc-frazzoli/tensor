// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Drop.html">Drop</a> */
public enum Drop {
  ;
  /** gives tensor with its first n elements dropped
   * 
   * @param tensor
   * @param n
   * @return
   * @throws Exception if given tensor has less than n entries */
  public static Tensor head(Tensor tensor, int n) {
    return tensor.extract(n, tensor.length());
  }

  /** gives tensor with its last n elements dropped
   * 
   * @param tensor
   * @param n
   * @return
   * @throws Exception if given tensor has less than n entries */
  public static Tensor tail(Tensor tensor, int n) {
    return tensor.extract(0, tensor.length() - n);
  }
}
