// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.List;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/UpperTriangularize.html">UpperTriangularize</a> */
public enum UpperTriangularize {
  ;
  /** @param matrix
   * @return */
  public static Tensor of(Tensor matrix) {
    return of(matrix, 0);
  }

  /** @param matrix
   * @param k
   * @return */
  public static Tensor of(Tensor matrix, int k) {
    List<Integer> dims = Dimensions.of(matrix);
    return Tensors.matrix((i, j) -> k <= j - i ? matrix.get(i, j) : RealScalar.ZERO, dims.get(0), dims.get(1));
  }
}
