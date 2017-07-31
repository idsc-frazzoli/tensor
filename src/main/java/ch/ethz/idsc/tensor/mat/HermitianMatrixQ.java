// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.MatrixQ;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/HermitianMatrixQ.html">HermitianMatrixQ</a> */
public enum HermitianMatrixQ {
  ;
  /** @param tensor
   * @return true if tensor is a hermitian matrix */
  public static boolean of(Tensor tensor) {
    return MatrixQ.of(tensor) && tensor.equals(ConjugateTranspose.of(tensor));
  }
}
