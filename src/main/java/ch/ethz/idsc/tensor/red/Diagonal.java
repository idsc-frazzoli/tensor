// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.List;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.MatrixQ;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Diagonal.html">Diagonal</a> */
public enum Diagonal {
  ;
  /** @param matrix
   * @return vector of entries on diagonal of given matrix
   * @throws Exception if input tensor is not a matrix */
  public static Tensor of(Tensor matrix) {
    if (!MatrixQ.of(matrix))
      throw TensorRuntimeException.of(matrix);
    List<Integer> dims = Dimensions.of(matrix);
    return Tensors.vector(i -> matrix.get(i, i), Math.min(dims.get(0), dims.get(1)));
  }
}
