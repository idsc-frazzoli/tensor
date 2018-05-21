// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;

/** consistent with Mathematica, in particular DiagonalMatrix[{}] results in error.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/DiagonalMatrix.html">DiagonalMatrix</a> */
public enum DiagonalMatrix {
  ;
  /** @param vector with scalars to appear on the diagonal
   * @return
   * @throws Exception if given vector is empty or a {@link Scalar} */
  public static Tensor with(Tensor vector) {
    int length = vector.length();
    if (0 < length)
      return Tensors.matrix((i, j) -> i.equals(j) ? vector.Get(i) : vector.Get(i).zero(), length, length);
    throw TensorRuntimeException.of(vector);
  }

  /** @param scalars
   * @return square matrix with scalars along diagonal
   * @throws Exception if list of scalars is empty */
  public static Tensor of(Scalar... scalars) {
    return with(Tensors.of(scalars));
  }

  /** @param numbers
   * @return square matrix with numbers as {@link RealScalar}s along diagonal
   * @throws Exception if list of numbers is empty */
  public static Tensor of(Number... numbers) {
    return with(Tensors.vector(numbers));
  }
}
