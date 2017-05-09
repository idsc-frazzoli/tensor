// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Transpose;

/** <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/SymmetricMatrixQ.html">SymmetricMatrixQ</a> */
public enum SymmetricMatrixQ {
  ;
  /** @param matrix
   * @return true if matrix is explicitly symmetric */
  public static boolean of(Tensor matrix) {
    return matrix.equals(Transpose.of(matrix));
  }
}
