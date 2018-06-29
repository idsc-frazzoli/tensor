// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.Unprotect;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/LowerTriangularize.html">LowerTriangularize</a> */
public enum LowerTriangularize {
  ;
  /** retains the entries on the diagonal and below
   * 
   * @param matrix
   * @return */
  public static Tensor of(Tensor matrix) {
    return of(matrix, 0);
  }

  /** Example:
   * LowerTriangularize.of(matrix, -1)
   * retains the entries strictly lower than the diagonal
   * 
   * @param matrix
   * @param k
   * @return */
  public static Tensor of(Tensor matrix, int k) {
    return Tensors.matrix((i, j) -> j - i <= k ? matrix.Get(i, j) : matrix.Get(i, j).zero(), //
        matrix.length(), Unprotect.dimension1(matrix));
  }
}
