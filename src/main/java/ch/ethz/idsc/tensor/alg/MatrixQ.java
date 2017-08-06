// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/MatrixQ.html">MatrixQ</a> */
public enum MatrixQ {
  ;
  /** @param tensor
   * @return true if tensor is a matrix */
  public static boolean of(Tensor tensor) {
    return ArrayQ.ofRank(tensor, 2);
  }

  /** @param tensor
   * @param rows
   * @param cols
   * @return */
  public static boolean ofSize(Tensor tensor, int rows, int cols) {
    return Dimensions.isArrayWithDimensions(tensor, Arrays.asList(rows, cols));
  }
}
