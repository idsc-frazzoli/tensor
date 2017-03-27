// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ZeroScalar;

class NormInfinity extends RankAdapter<RealScalar> {
  @Override
  public RealScalar ofScalar(Scalar scalar) {
    return (RealScalar) scalar.abs();
  }

  // max(|a_1|, ..., |a_n|)
  @Override
  public RealScalar ofVector(Tensor vector) {
    return vector.flatten(0) //
        .map(Scalar.class::cast) //
        .map(Scalar::abs) //
        .map(RealScalar.class::cast) //
        .reduce(RealScalar::max) //
        .orElse(ZeroScalar.get());
  }

  @Override
  public RealScalar ofMatrix(Tensor matrix) {
    return ofVector(Tensor.of(matrix.flatten(0).map(Norm._1::of)));
  }
}
