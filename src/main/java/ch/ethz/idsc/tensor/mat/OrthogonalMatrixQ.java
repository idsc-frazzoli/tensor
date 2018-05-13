// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.MatrixQ;
import ch.ethz.idsc.tensor.alg.Transpose;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/OrthogonalMatrixQ.html">OrthogonalMatrixQ</a> */
public enum OrthogonalMatrixQ {
  ;
  /** Mathematica definition:
   * "A matrix m is orthogonal if m.Transpose[m] is the identity matrix."
   * 
   * @param tensor
   * @return true, if tensor is a matrix and tensor.Transpose[tensor] is the identity matrix
   * @see UnitaryMatrixQ */
  public static boolean of(Tensor tensor) {
    return MatrixQ.of(tensor) && //
        NullSpace.CHOP_DEFAULT.close(tensor.dot(Transpose.of(tensor)), IdentityMatrix.of(tensor.length()));
  }
}
