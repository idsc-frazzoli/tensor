// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Abs;

/** 1-norm, for vectors |a_1| + ... + |a_n| also known as ManhattanDistance */
/* package */ enum Norm1 implements NormInterface {
  INSTANCE;
  // ---
  @Override // from VectorNormInterface
  public Scalar ofVector(Tensor vector) {
    return ofStream(vector.stream());
  }

  @Override // from NormInterface
  public Scalar ofMatrix(Tensor matrix) {
    return Total.of(Abs.of(matrix)).stream().reduce(Max::of).get().Get();
  }

  /* package */ static Scalar ofStream(Stream<Tensor> stream) {
    return stream //
        .map(Scalar.class::cast) //
        .map(Scalar::abs) //
        .reduce(Scalar::add).get();
  }
}
