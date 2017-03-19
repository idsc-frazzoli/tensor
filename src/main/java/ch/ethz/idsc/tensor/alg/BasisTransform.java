// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;

public class BasisTransform {
  /** @param form
   * @param v matrix
   * @return */
  public static Tensor ofForm(Tensor form, Tensor v) {
    final int rank = TensorRank.of(form);
    int[] sigma = new int[rank]; // [1, 2, ..., r, 0]
    for (int c1 = 1; c1 < rank; ++c1)
      sigma[c1 - 1] = c1;
    for (int index = 0; index < rank; ++index)
      form = Transpose.of(form, sigma).dot(v);
    return form;
  }
}
