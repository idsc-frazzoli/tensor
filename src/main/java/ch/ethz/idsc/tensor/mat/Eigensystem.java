// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Eigensystem.html">Eigensystem</a> */
public interface Eigensystem {
  /** @param matrix symmetric
   * @return */
  static Eigensystem ofSymmetric(Tensor matrix) {
    if (!SymmetricMatrixQ.of(matrix))
      throw TensorRuntimeException.of(matrix);
    // TODO
    throw TensorRuntimeException.of(matrix);
  }

  /** @return matrix with eigenvectors of given matrix as rows */
  Tensor vectors();

  /** @return vector of eigenvalues corresponding to the eigenvectors */
  Tensor values();
}
