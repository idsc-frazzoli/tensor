// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.Optional;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.alg.TensorRank;

/* package */ class RankAdapter<Type> {
  private static final int RANK_SCALAR = 0;
  private static final int RANK_VECTOR = 1;
  private static final int RANK_MATRIX = 2;

  @SuppressWarnings("incomplete-switch")
  public final Type of(Tensor tensor) {
    Optional<Integer> rank = TensorRank.ofArray(tensor);
    if (rank.isPresent())
      switch (rank.get()) {
      case RANK_SCALAR:
        return ofScalar((Scalar) tensor);
      case RANK_VECTOR:
        return ofVector(tensor);
      case RANK_MATRIX:
        return ofMatrix(tensor);
      }
    return ofOther(tensor);
  }

  public Type ofScalar(Scalar scalar) {
    return ofOther(scalar);
  }

  public Type ofVector(Tensor vector) {
    return ofOther(vector);
  }

  public Type ofMatrix(Tensor matrix) {
    return ofOther(matrix);
  }

  public Type ofOther(Tensor tensor) {
    throw TensorRuntimeException.of(tensor);
  }
}
