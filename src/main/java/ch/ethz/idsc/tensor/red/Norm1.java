// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Abs;

/* package */ class Norm1 extends RankAdapter<Scalar> {
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
        .get();
  }

  @Override
  public Scalar ofMatrix(Tensor matrix) {
    return Total.of(Abs.of(matrix)).flatten(0) //
        .map(Scalar.class::cast) //
        .reduce(Max::of) //
        .get();
  }
}
