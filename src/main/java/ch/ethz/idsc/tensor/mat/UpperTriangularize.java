// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.Unprotect;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/UpperTriangularize.html">UpperTriangularize</a> */
public enum UpperTriangularize {
  ;
  /** retains the entries on the diagonal and above
   * 
   * @param matrix
   * @return */
  public static Tensor of(Tensor matrix) {
    return of(matrix, 0);
  }

  /** Example:
   * UpperTriangularize.of(matrix, 1)
   * retains the entries strictly above the diagonal
   * 
   * @param matrix
   * @param k
   * @return */
  public static Tensor of(Tensor matrix, int k) {
    return Tensors.matrix((i, j) -> k <= j - i ? matrix.Get(i, j) : matrix.Get(i, j).zero(), //
        matrix.length(), Unprotect.dimension1(matrix));
  }
}
