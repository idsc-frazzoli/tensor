// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Abs;

/** 1-norm, for vectors |a_1| + ... + |a_n| also known as ManhattanDistance */
/* package */ enum Norm1 implements NormInterface {
  INSTANCE;
  @Override
  public Scalar ofVector(Tensor vector) {
    return vector.stream() //
        .map(Scalar.class::cast) //
        .map(Scalar::abs) //
        .reduce(Scalar::add).get();
  }

  @Override
  public Scalar ofMatrix(Tensor matrix) {
    return Total.of(Abs.of(matrix)).stream() //
        .map(Scalar.class::cast) //
        .reduce(Max::of).get();
  }
}
