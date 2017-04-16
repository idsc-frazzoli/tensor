// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/QRDecomposition.html">QRDecomposition</a> */
public interface QRDecomposition {
  /** @param matrix
   * @return qr-decomposition of matrix */
  public static QRDecomposition of(Tensor matrix) {
    return new QRDecompositionImpl(matrix.unmodifiable());
  }

  /** @return orthogonal matrix */
  Tensor getInverseQ();

  /** @return upper triangular matrix */
  Tensor getR();

  /** @return orthogonal matrix */
  Tensor getQ();
}
