// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ZeroScalar;

class Norm2Squared extends RankAdapter<RealScalar> {
  @Override
  public RealScalar ofScalar(Scalar scalar) {
    return (RealScalar) scalar.absSquared();
  }

  @Override
  public RealScalar ofVector(Tensor vector) {
    return (RealScalar) vector.flatten(0) //
        .map(Scalar.class::cast) //
        .map(Scalar::absSquared) //
        .reduce(Scalar::add) //
        .orElse(ZeroScalar.get());
  }

  @Override
  public RealScalar ofMatrix(Tensor matrix) {
    Scalar value = Norm._2.of(matrix);
    return (RealScalar) value.multiply(value);
  }
}
