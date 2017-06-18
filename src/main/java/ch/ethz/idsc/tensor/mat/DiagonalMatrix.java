// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/DiagonalMatrix.html">DiagonalMatrix</a> */
public enum DiagonalMatrix {
  ;
  /** @param vector with scalars to appear on the diagonal
   * @return */
  public static Tensor of(Tensor vector) {
    if (vector.isScalar())
      throw TensorRuntimeException.of(vector);
    return Tensors.matrix((i, j) -> i.equals(j) ? vector.Get(i) : vector.Get(i).zero(), vector.length(), vector.length());
  }

  /** @param scalars
   * @return matrix with scalars along diagonal */
  public static Tensor of(Scalar... scalars) {
    return of(Tensors.of(scalars));
  }

  /** @param numbers
   * @return matrix with numbers as {@link RealScalar}s along diagonal */
  public static Tensor of(Number... numbers) {
    return of(Tensors.vector(numbers));
  }
}
