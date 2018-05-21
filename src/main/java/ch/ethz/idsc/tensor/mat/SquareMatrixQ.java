// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.alg.MatrixQ;

/** consistent with Mathematica, in particular SquareMatrixQ[{}] == false
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/SquareMatrixQ.html">SquareMatrixQ</a> */
public enum SquareMatrixQ {
  ;
  /** @param tensor
   * @return true if tensor is a square matrix, otherwise false */
  public static boolean of(Tensor tensor) {
    return MatrixQ.of(tensor) && tensor.length() == tensor.stream().findFirst().get().length();
  }

  /** @param tensor
   * @return given tensor
   * @throws Exception if given tensor is not a square matrix */
  public static Tensor require(Tensor tensor) {
    if (of(tensor))
      return tensor;
    throw TensorRuntimeException.of(tensor);
  }
}
