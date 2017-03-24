// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.ZeroScalar;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/DiagonalMatrix.html">DiagonalMatrix</a> */
public enum DiagonalMatrix {
  ;
  /** @param d vector with scalars to appear on the diagonal
   * @return */
  public static Tensor of(Tensor d) { // <- temporary, requires d to be a vector
    final int n = d.length();
    return Tensors.matrix((i, j) -> i.equals(j) ? d.Get(i) : ZeroScalar.get(), n, n);
  }
}
