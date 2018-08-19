// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Unprotect;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/MatrixLog.html">MatrixLog</a> */
public enum MatrixLog {
  ;
  /** Hint: currently only matrices of dimensions 2x2 are supported
   * 
   * @param matrix
   * @return
   * @throws Exception if computation is not supported for given matrix */
  public static Tensor of(Tensor matrix) {
    int dim1 = Unprotect.dimension1(matrix);
    if (matrix.length() == 2) {
      if (dim1 == 2)
        return MatrixLog2.of(matrix);
    }
    throw new UnsupportedOperationException();
  }
}
