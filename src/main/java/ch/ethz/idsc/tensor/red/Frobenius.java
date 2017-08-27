// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

public enum Frobenius implements NormInterface {
  NORM;
  // ---
  @Override
  public Scalar vector(Tensor vector) {
    return of(vector);
  }

  @Override
  public Scalar matrix(Tensor matrix) {
    return of(matrix);
  }

  /** @param tensor of arbitrary rank
   * @return Frobenius norm of given tensor */
  public static Scalar of(Tensor tensor) {
    return Norm._2.vector(Tensor.of(tensor.flatten(-1)));
  }
}
