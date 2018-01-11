// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Eigensystem.html">Eigensystem</a> */
public interface Eigensystem {
  /** @param matrix symmetric and real valued
   * @return
   * @throws Exception if input is not a symmetric matrix */
  static Eigensystem ofSymmetric(Tensor matrix) {
    if (!SymmetricMatrixQ.of(matrix))
      throw TensorRuntimeException.of(matrix);
    return new JacobiMethod(matrix);
  }

  /** Careful: Mathematica orders the eigenvalues according to absolute value.
   * However, the tensor library does not guarantee any particular ordering.
   * 
   * @return vector of eigenvalues corresponding to the eigenvectors */
  Tensor values();

  /** @return matrix with rows as eigenvectors of given matrix
   * The eigenvectors are not necessarily scaled to unit length. */
  Tensor vectors();
}
