// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Unprotect;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Orthogonalize.html">Orthogonalize</a> */
public enum Orthogonalize {
  ;
  /** @param matrix with rows <= cols
   * @return matrix orthogonal to input
   * @see OrthogonalMatrixQ
   * @see UnitaryMatrixQ */
  public static Tensor of(Tensor matrix) {
    int dim0 = matrix.length();
    int dim1 = Unprotect.length0(matrix);
    if (dim1 < dim0)
      throw TensorRuntimeException.of(matrix);
    // old impl
    // QRDecomposition qrDecomposition = QRDecomposition.of(Transpose.of(matrix));
    // return Transpose.of(qrDecomposition.getQ()).extract(0, matrix.length());
    // TODO check whether ConjugateTranspose or Transpose should be used, also getQ or getInverseQ?
    return QRDecomposition.of(ConjugateTranspose.of(matrix)).getInverseQ().extract(0, matrix.length());
  }
}
