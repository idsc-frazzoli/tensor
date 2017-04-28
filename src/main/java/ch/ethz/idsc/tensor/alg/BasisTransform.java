// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.mat.LinearSolve;

/** transform a (r,s)-tensor to a new basis */
public enum BasisTransform {
  ;
  /** @param form is a (0,s)-tensor
   * @param v matrix
   * @return tensor of form with respect to basis v */
  public static Tensor ofForm(Tensor form, Tensor v) {
    return Transpose.apply(form, tensor -> tensor.dot(v));
  }

  /** @param matrix is (1,1)-tensor
   * @param v matrix
   * @return */
  /* package until approved */ static Tensor ofMatrix(Tensor matrix, Tensor v) {
    return LinearSolve.of(v, matrix.dot(v)); // TODO
  }
  // TODO general for (r,s)-tensors
}
