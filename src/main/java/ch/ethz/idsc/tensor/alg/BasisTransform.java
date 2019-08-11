// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.mat.LinearSolve;
import ch.ethz.idsc.tensor.red.Nest;

/** transform a (r, s)-tensor to a new basis */
// LONGTERM general for (r, s)-tensors
public enum BasisTransform {
  ;
  /** @param form is a (0, s)-tensor with all dimensions equal
   * @param v matrix not necessarily square
   * @return tensor of form with respect to basis v
   * @throws Exception if form is not a regular array, or v is not a matrix */
  public static Tensor ofForm(Tensor form, Tensor v) {
    return nestRank(form, tensor -> tensor.dot(v));
  }

  /** @param matrix is (1, 1)-tensor
   * @param v square matrix with full rank
   * @return */
  /* package */ static Tensor ofMatrix(Tensor matrix, Tensor v) {
    return LinearSolve.of(v, matrix.dot(v));
  }

  /** @param tensor
   * @param operator
   * @return function applied along each dimension of tensor
   * @see Nest */
  /* package */ static Tensor nestRank(Tensor tensor, UnaryOperator<Tensor> operator) {
    int rank = TensorRank.of(tensor);
    Integer[] sigma = IntStream.range(1, rank + 1) //
        .map(index -> index % rank) //
        .boxed() //
        .toArray(Integer[]::new);
    for (int index = 0; index < sigma.length; ++index)
      tensor = Transpose.of(operator.apply(tensor), sigma); // [1, 2, ..., r, 0]
    return tensor;
  }
}
