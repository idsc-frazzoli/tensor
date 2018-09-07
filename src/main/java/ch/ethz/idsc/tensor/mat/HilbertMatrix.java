// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/HilbertMatrix.html">HilbertMatrix</a> */
public enum HilbertMatrix {
  ;
  /** @param n rows greater than zero
   * @param m columns greater than zero
   * @return n x m Hilbert matrix with elements of the form 1/(i+j-1)
   * @throws Exception if input parameters are outside range */
  public static Tensor of(int n, int m) {
    if (0 < n && 0 < m)
      return Tensors.matrix((i, j) -> RationalScalar.of(1, i + j + 1), n, m);
    throw new RuntimeException(String.format("HilbertMatrix[%d,%d]", n, m));
  }

  /** @param n rows greater than zero
   * @return n x n Hilbert matrix with elements of the form 1/(i+j-1)
   * @throws Exception if input parameters are outside range */
  public static Tensor of(int n) {
    return of(n, n);
  }
}
