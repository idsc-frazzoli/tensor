// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** The implementation is consistent with Mathematica::MatrixQ, in particular
 * MatrixQ[ {} ] == false
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/MatrixQ.html">MatrixQ</a> */
public enum MatrixQ {
  ;
  /** @param tensor
   * @return true if tensor is a matrix */
  public static boolean of(Tensor tensor) {
    return Dimensions.isArrayWithRank(tensor, 2);
  }

  /** @param tensor
   * @param rows
   * @param cols
   * @return true if tensor is a matrix with given number of rows and columns */
  public static boolean ofSize(Tensor tensor, int rows, int cols) {
    return Dimensions.isArrayWithDimensions(tensor, Arrays.asList(rows, cols));
  }

  /** @param tensor
   * @return given tensor
   * @throws Exception if given tensor is not a matrix */
  public static Tensor require(Tensor tensor) {
    if (of(tensor))
      return tensor;
    throw TensorRuntimeException.of(tensor);
  }

  /** @param tensor
   * @param rows
   * @param cols
   * @return given tensor
   * @throws Exception if given tensor is not a matrix with given number of rows and columns */
  public static Tensor requireSize(Tensor tensor, int rows, int cols) {
    if (ofSize(tensor, rows, cols))
      return tensor;
    throw TensorRuntimeException.of(tensor);
  }
}
