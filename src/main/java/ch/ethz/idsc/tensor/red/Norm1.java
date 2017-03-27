// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ZeroScalar;
import ch.ethz.idsc.tensor.alg.Transpose;

class Norm1 extends RankAdapter<RealScalar> {
  @Override
  public RealScalar ofScalar(Scalar scalar) {
    return (RealScalar) scalar.abs();
  }

  // |a_1| + ... + |a_n|
  // also known as ManhattanDistance
  @Override
  public RealScalar ofVector(Tensor vector) {
    return (RealScalar) vector.flatten(0) //
        .map(Scalar.class::cast) //
        .map(Scalar::abs) //
        .reduce(Scalar::add) //
        .orElse(ZeroScalar.get());
  }

  @Override
  public RealScalar ofMatrix(Tensor matrix) {
    return Transpose.of(matrix).flatten(0) //
        .map(this::ofVector) //
        .reduce(RealScalar::max) //
        .orElse(ZeroScalar.get());
  }
}
