// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/VectorQ.html">VectorQ</a> */
public enum VectorQ {
  ;
  /** @param tensor
   * @return true if tensor is a vector */
  public static boolean of(Tensor tensor) {
    return ArrayQ.ofRank(tensor, 1);
  }

  /** @param tensor
   * @param length
   * @return true if tensor is a vector with given length */
  public static boolean ofLength(Tensor tensor, int length) {
    return tensor.length() == length && of(tensor);
  }
}
