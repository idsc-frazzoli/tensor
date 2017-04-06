// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ZeroScalar;
import ch.ethz.idsc.tensor.alg.Transpose;

class Norm1 extends RankAdapter<Scalar> {
  @Override
  public Scalar ofScalar(Scalar scalar) {
    return scalar.abs();
  }

  // |a_1| + ... + |a_n|
  // also known as ManhattanDistance
  @Override
  public Scalar ofVector(Tensor vector) {
    return vector.flatten(0) //
        .map(Scalar.class::cast) //
        .map(Scalar::abs) //
        .reduce(Scalar::add) //
        .orElse(ZeroScalar.get());
  }

  @Override
  public Scalar ofMatrix(Tensor matrix) {
    return Transpose.of(matrix).flatten(0) //
        .map(this::ofVector) //
        .map(Scalar.class::cast) //
        .reduce(Max::of) //
        .orElse(ZeroScalar.get());
  }
}
