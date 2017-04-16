// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;

public enum BasisTransform {
  ;
  /** @param form
   * @param v matrix
   * @return tensor of form with respect to basis v */
  public static Tensor ofForm(Tensor form, Tensor v) {
    return Transpose.apply(form, tensor -> tensor.dot(v));
  }
}
