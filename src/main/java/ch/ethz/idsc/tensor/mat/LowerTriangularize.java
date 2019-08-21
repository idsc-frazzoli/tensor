// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.Unprotect;

/** consistent with Mathematica:
 * <pre>
 * LowerTriangularize[{{1}}, +1] == {{1}}
 * LowerTriangularize[{{1}}, +0] == {{1}}
 * LowerTriangularize[{{1}}, -1] == {{0}}
 * </pre>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/LowerTriangularize.html">LowerTriangularize</a> */
public enum LowerTriangularize {
  ;
  /** retains the entries on the diagonal and below
   * 
   * @param matrix
   * @return
   * @throws Exception if given matrix is not an array of rank 2 */
  public static Tensor of(Tensor matrix) {
    return of(matrix, 0);
  }

  /** Example:
   * LowerTriangularize.of(matrix, -1)
   * retains the entries strictly lower than the diagonal
   * 
   * @param matrix
   * @param k
   * @return
   * @throws Exception if given matrix is not an array of rank 2 */
  public static Tensor of(Tensor matrix, int k) {
    return Tensors.matrix((i, j) -> j - i <= k ? matrix.Get(i, j) : matrix.Get(i, j).zero(), //
        matrix.length(), Unprotect.dimension1(matrix));
  }
}
