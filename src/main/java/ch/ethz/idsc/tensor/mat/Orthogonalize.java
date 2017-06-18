// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.List;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.Transpose;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Orthogonalize.html">Orthogonalize</a> */
public enum Orthogonalize {
  ;
  /** @param matrix with rows <= cols
   * @return matrix orthogonal to input */
  public static Tensor of(Tensor matrix) {
    List<Integer> list = Dimensions.of(matrix);
    if (list.get(1) < list.get(0))
      throw TensorRuntimeException.of(matrix);
    QRDecomposition qrDecomposition = QRDecomposition.of(Transpose.of(matrix));
    return Transpose.of(qrDecomposition.getQ()).extract(0, matrix.length());
  }
}
