// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Unprotect;
import ch.ethz.idsc.tensor.lie.QRDecomposition;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Orthogonalize.html">Orthogonalize</a> */
public enum Orthogonalize {
  ;
  /** @param matrix with rows <= cols
   * @return matrix with pairwise orthogonal row vectors with the same span as input vectors
   * @see OrthogonalMatrixQ
   * @see UnitaryMatrixQ */
  public static Tensor of(Tensor matrix) {
    int dim0 = matrix.length();
    int dim1 = Unprotect.dimension1(matrix);
    if (dim1 < dim0)
      throw TensorRuntimeException.of(matrix);
    return QRDecomposition.of(ConjugateTranspose.of(matrix)).getInverseQ().extract(0, matrix.length());
  }
}
