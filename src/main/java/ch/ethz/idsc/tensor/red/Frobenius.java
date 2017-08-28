// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** implementation consistent with Mathematica
 * Norm[{3, 4}, "Frobenius"] == 5 */
public enum Frobenius implements NormInterface {
  NORM;
  // ---
  @Override
  public Scalar ofVector(Tensor vector) {
    return of(vector);
  }

  @Override
  public Scalar ofMatrix(Tensor matrix) {
    return of(matrix);
  }

  /** @param tensor of arbitrary rank
   * @return Frobenius norm of given tensor */
  public Scalar of(Tensor tensor) {
    return Norm._2.ofVector(Tensor.of(tensor.flatten(-1)));
  }
}
