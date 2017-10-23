// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.MatrixQ;
import ch.ethz.idsc.tensor.alg.VectorQ;

/** implementation consistent with Mathematica
 * Norm[{3, 4}, "Frobenius"] == 5 */
public enum Frobenius implements NormInterface {
  NORM;
  // ---
  @Override
  public Scalar ofVector(Tensor vector) {
    VectorQ.orThrow(vector);
    return of(vector);
  }

  @Override
  public Scalar ofMatrix(Tensor matrix) {
    MatrixQ.orThrow(matrix);
    return of(matrix);
  }

  /** @param tensor of arbitrary rank
   * @return Frobenius norm of given tensor */
  public Scalar of(Tensor tensor) {
    return Norm._2.ofVector(Tensor.of(tensor.flatten(-1)));
  }

  /** @param t1
   * @param t2
   * @return Frobenius norm of tensor difference || t1 - t2 || */
  public Scalar between(Tensor t1, Tensor t2) {
    return of(t1.subtract(t2));
  }
}
