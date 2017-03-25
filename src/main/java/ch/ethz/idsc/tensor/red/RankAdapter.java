// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.TensorRank;

/* package */ class RankAdapter<Type> {
  public static final int RANK_SCALAR = 0;
  public static final int RANK_VECTOR = 1;
  public static final int RANK_MATRIX = 2;

  @SuppressWarnings("incomplete-switch")
  public final Type of(Tensor tensor) {
    if (Dimensions.isArray(tensor))
      switch (TensorRank.of(tensor)) {
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
    throw new UnsupportedOperationException();
  }
}
