// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.mat.LinearSolve;

/** transform a (r,s)-tensor to a new basis */
public enum BasisTransform {
  ;
  /** @param form is a (0,s)-tensor with all dimensions equal
   * @param v matrix not necessarily square
   * @return tensor of form with respect to basis v
   * @throws Exception if form is not a regular array, or v is not a matrix */
  public static Tensor ofForm(Tensor form, Tensor v) {
    return StaticHelper.nestRank(form, tensor -> tensor.dot(v));
  }

  /** @param matrix is (1,1)-tensor
   * @param v square matrix with full rank
   * @return */
  /* package until approved */ static Tensor ofMatrix(Tensor matrix, Tensor v) {
    return LinearSolve.of(v, matrix.dot(v));
  }
  // LONGTERM general for (r,s)-tensors
}
