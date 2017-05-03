// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;

/** <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/HermitianMatrixQ.html">HermitianMatrixQ</a> */
public enum HermitianMatrixQ {
  ;
  /** @param matrix
   * @return true if matrix is hermitian */
  public static boolean of(Tensor matrix) {
    return matrix.equals(ConjugateTranspose.of(matrix));
  }
}
