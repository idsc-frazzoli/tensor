// code by jph
package ch.ethz.idsc.tensor.opt.hun;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/* package */ enum ScalarArray {
  ;
  /** @param vector
   * @return
   * @throws Exception if given vector is not a tensor of rank 1 */
  static Scalar[] ofVector(Tensor vector) {
    return vector.stream() //
        .map(Scalar.class::cast) //
        .toArray(Scalar[]::new);
  }

  /** @param matrix
   * @return
   * @throws Exception if given matrix is not a tensor of rank 2 */
  static Scalar[][] ofMatrix(Tensor matrix) {
    return matrix.stream() //
        .map(ScalarArray::ofVector) //
        .toArray(Scalar[][]::new);
  }
}
