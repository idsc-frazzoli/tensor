// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.sca.Chop;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/OrthogonalMatrixQ.html">OrthogonalMatrixQ</a> */
public enum OrthogonalMatrixQ {
  ;
  /** Mathematica definition:
   * "A matrix m is orthogonal if m.Transpose[m] is the identity matrix."
   * 
   * @param matrix
   * @return true, if matrix.Transpose[matrix] is the identity matrix */
  public static boolean of(Tensor matrix) {
    if (!MatrixQ.of(matrix))
      return false;
    return Chop.isZeros(matrix.dot(Transpose.of(matrix)).subtract(IdentityMatrix.of(matrix.length())));
  }
}
