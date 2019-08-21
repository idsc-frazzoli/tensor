// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.List;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.alg.Dimensions;

/** consistent with Mathematica, in particular SquareMatrixQ[{}] == false
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/SquareMatrixQ.html">SquareMatrixQ</a> */
public enum SquareMatrixQ {
  ;
  /** @param tensor
   * @return true if tensor is a square matrix, otherwise false */
  public static boolean of(Tensor tensor) {
    Dimensions dimensions = new Dimensions(tensor);
    List<Integer> list = dimensions.list();
    return list.size() == 2 //
        && list.get(0).equals(list.get(1)) //
        && dimensions.isArray();
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
