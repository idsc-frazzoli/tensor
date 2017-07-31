// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.MatrixQ;
import ch.ethz.idsc.tensor.alg.Transpose;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/SymmetricMatrixQ.html">SymmetricMatrixQ</a> */
public enum SymmetricMatrixQ {
  ;
  /** @param tensor
   * @return true if tensor is a symmetric matrix */
  public static boolean of(Tensor tensor) {
    return MatrixQ.of(tensor) && tensor.equals(Transpose.of(tensor));
  }
}
