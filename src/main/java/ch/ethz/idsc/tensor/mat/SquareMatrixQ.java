// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Unprotect;
import ch.ethz.idsc.tensor.alg.MatrixQ;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/SquareMatrixQ.html">SquareMatrixQ</a> */
public enum SquareMatrixQ {
  ;
  /** @param tensor
   * @return true if tensor is a square matrix, otherwise false */
  public static boolean of(Tensor tensor) {
    if (!MatrixQ.of(tensor))
      return false;
    return tensor.length() == Unprotect.length0(tensor);
  }
}
