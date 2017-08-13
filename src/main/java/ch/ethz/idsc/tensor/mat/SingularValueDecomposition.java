// code by jph
package ch.ethz.idsc.tensor.mat;

import java.io.Serializable;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.alg.MatrixQ;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/SingularValueDecomposition.html">SingularValueDecomposition</a> */
public interface SingularValueDecomposition extends Serializable {
  /** performs a singular value decomposition of matrix A
   * <ul>
   * <li>u.dot(DiagonalMatrix.of(values())).dot(Transpose.of(v)) == A
   * <li>Transpose.of(U).dot(U) == IdentityMatrix
   * <li>V.dot(Transpose.of(V) == IdentityMatrix
   * <li>Transpose.of(V).dot(V) == IdentityMatrix
   * </ul>
   * 
   * @param A is rows x cols matrix with rows >= cols
   * @return singular value decomposition of matrix A
   * @throws if decomposition cannot be established */
  static SingularValueDecomposition of(Tensor A) {
    if (!MatrixQ.of(A))
      throw TensorRuntimeException.of(A);
    return new SingularValueDecompositionImpl(A);
  }

  /** @return matrix of dimensions A, rows x cols */
  Tensor getU();

  /** @return vector of non-negative singular values */
  Tensor values();

  /** @return square matrix of dimensions cols x cols */
  Tensor getV();

  /** @param w_threshold strictly below which singular values are clipped to zero */
  void setThreshold(double w_threshold);

  /** @return threshold strictly below which singular values are considered to be zero */
  double getThreshold();
}
