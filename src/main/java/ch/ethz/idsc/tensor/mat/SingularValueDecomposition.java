// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/SingularValueDecomposition.html">SingularValueDecomposition</a> */
public interface SingularValueDecomposition {
  /** performs a singular value decomposition of matrix A
   * <ul>
   * <li>u.dot(DiagonalMatrix.of(w)).dot(Transpose.of(v)) == A
   * <li>Transpose.of(U).dot(U) == IdentityMatrix
   * <li>V.dot(Transpose.of(V) == IdentityMatrix
   * <li>Transpose.of(V).dot(V) == IdentityMatrix
   * </ul>
   * 
   * @param A is rows x cols matrix with rows >= cols
   * @return singular value decomposition of matrix A
   * @throws if decomposition cannot be established */
  public static SingularValueDecomposition of(Tensor A) {
    return new SingularValueDecompositionImpl(A, 1e-17, 25);
  }

  public Tensor getU();

  public Tensor getW();

  public Tensor getV();

  public void setThreshold(double w_threshold);

  /** @return threshold strictly below which singular values are considered to be zero */
  public double getThreshold();
}
