// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/* package */ enum ScalarArray {
  ;
  static Scalar[] ofVector(Tensor vector) {
    return vector.stream() //
        .map(Scalar.class::cast) //
        .toArray(Scalar[]::new);
  }

  static Scalar[][] ofMatrix(Tensor matrix) {
    return matrix.stream() //
        .map(ScalarArray::ofVector) //
        .toArray(Scalar[][]::new);
  }
}
