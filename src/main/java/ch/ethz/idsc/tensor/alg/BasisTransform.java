// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Tensor;

public enum BasisTransform {
  ;
  /** @param form
   * @param v matrix
   * @return tensor of form with respect to basis v */
  public static Tensor ofForm(Tensor form, Tensor v) {
    final int rank = TensorRank.of(form);
    Integer[] sigma = new Integer[rank]; // [1, 2, ..., r, 0]
    IntStream.range(0, rank).forEach(i -> sigma[i] = (i + 1) % rank);
    for (int index = 0; index < rank; ++index)
      form = Transpose.of(form.dot(v), sigma);
    return form;
  }
}
