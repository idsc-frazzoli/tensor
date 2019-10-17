// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Integers;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.mat.Inverse;
import ch.ethz.idsc.tensor.mat.LinearSolve;

/** transform a (r, s)-tensor to a new basis */
public enum BasisTransform {
  ;
  /** Hint: if r is non-zero then v has to be a square matrix with full rank
   * 
   * @param tensor of type (r, s)
   * @param r non-negative
   * @param v matrix
   * @return
   * @throws Exception if r is negative */
  public static Tensor of(Tensor tensor, int r, Tensor v) {
    int rank = TensorRank.of(tensor);
    Tensor inverse = 0 < Integers.requirePositiveOrZero(r) //
        ? Transpose.of(Inverse.of(v))
        : null;
    Integer[] sigma = IntStream.range(rank - 1, 2 * rank - 1) //
        .map(index -> index % rank) //
        .boxed() //
        .toArray(Integer[]::new); // [r, 0, 1, ..., r - 1]
    for (int index = 0; index < rank; ++index)
      tensor = Transpose.of(tensor, sigma).dot(index < r ? inverse : v);
    return tensor;
  }

  /** @param form is a (0, s)-tensor with all dimensions equal
   * @param v matrix not necessarily square
   * @return tensor of form with respect to basis v
   * @throws Exception if form is not a regular array, or v is not a matrix */
  public static Tensor ofForm(Tensor form, Tensor v) {
    return of(form, 0, v);
  }

  /** @param matrix is (1, 1)-tensor
   * @param v square matrix with full rank
   * @return */
  public static Tensor ofMatrix(Tensor matrix, Tensor v) {
    return LinearSolve.of(v, matrix.dot(v));
  }
}
