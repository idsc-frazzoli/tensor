// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** infinity-norm, for vectors max_i |a_i| */
/* package */ enum NormInfinity implements NormInterface {
  INSTANCE;
  // ---
  @Override // from VectorNormInterface
  public Scalar ofVector(Tensor vector) {
    return vector.stream() //
        .map(Scalar.class::cast) //
        .map(Scalar::abs) //
        .reduce(Max::of).get();
  }

  @Override // from NormInterface
  public Scalar ofMatrix(Tensor matrix) {
    return ofVector(Tensor.of(matrix.stream().map(Norm1.INSTANCE::ofVector)));
  }
}
