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
   * @param tensor
   * @return true, if tensor is a matrix and tensor.Transpose[tensor] is the identity matrix */
  public static boolean of(Tensor tensor) {
    return MatrixQ.of(tensor) && //
        Chop._12.allZero(tensor.dot(Transpose.of(tensor)).subtract(IdentityMatrix.of(tensor.length())));
  }
}
