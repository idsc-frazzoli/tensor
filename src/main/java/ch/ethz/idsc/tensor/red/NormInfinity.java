// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** infinity-norm, for vectors max_i |a_i| */
/* package */ enum NormInfinity implements NormInterface {
  INSTANCE;
  @Override
  public Scalar ofVector(Tensor vector) {
    return vector.flatten(0) //
        .map(Scalar.class::cast) //
        .map(Scalar::abs) //
        .reduce(Max::of).get();
  }

  @Override
  public Scalar ofMatrix(Tensor matrix) {
    return ofVector(Tensor.of(matrix.flatten(0).map(Norm1.INSTANCE::ofVector)));
  }
}
