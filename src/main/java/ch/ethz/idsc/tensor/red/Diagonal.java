// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.SquareMatrixQ;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Diagonal.html">Diagonal</a> */
public enum Diagonal {
  ;
  /** @param matrix square
   * @return vector of entries on diagonal of given matrix
   * @throws Exception if matrix is not a square matrix */
  public static Tensor of(Tensor matrix) {
    if (!SquareMatrixQ.of(matrix))
      throw TensorRuntimeException.of(matrix);
    return Tensors.vector(i -> matrix.get(i, i), matrix.length());
  }
}
