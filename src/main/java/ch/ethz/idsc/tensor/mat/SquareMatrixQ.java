// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.List;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Dimensions;
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
    List<Integer> list = Dimensions.of(tensor);
    return list.get(0).equals(list.get(1));
  }
}
