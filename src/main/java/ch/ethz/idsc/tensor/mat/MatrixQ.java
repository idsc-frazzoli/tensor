// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.ArrayQ;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/MatrixQ.html">MatrixQ</a> */
public enum MatrixQ {
  ;
  /** @param tensor
   * @return true if tensor is a matrix */
  public static boolean of(Tensor tensor) {
    return ArrayQ.ofRank(tensor, 2);
  }
}
