// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ZeroScalar;

/* package */ class NormInfinity extends RankAdapter<Scalar> {
  @Override
  public Scalar ofScalar(Scalar scalar) {
    return scalar.abs();
  }

  // max(|a_1|, ..., |a_n|)
  @Override
  public Scalar ofVector(Tensor vector) {
    return vector.flatten(0) //
        .map(Scalar.class::cast) //
        .map(Scalar::abs) //
        .reduce(Max::of) //
        .orElse(ZeroScalar.get());
  }

  @Override
  public Scalar ofMatrix(Tensor matrix) {
    return ofVector(Tensor.of(matrix.flatten(0).map(Norm._1::of)));
  }
}
