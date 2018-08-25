// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.red.KroneckerDelta;

/** implementation is consistent with Mathematica.
 * 
 * <pre>
 * IdentityMatrix[2] == {{1, 0}, {0, 1}}
 * </pre>
 * 
 * For non-positive input:
 * <pre>
 * IdentityMatrix[0] => Exception
 * IdentityMatrix[-3] => Exception
 * </pre>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/IdentityMatrix.html">IdentityMatrix</a> */
public enum IdentityMatrix {
  ;
  /** @param n positive
   * @return identity matrix of dimensions n x n
   * @throws Exception if n is negative or zero */
  public static Tensor of(int n) {
    if (0 < n)
      return Tensors.matrix(KroneckerDelta::of, n, n);
    throw new RuntimeException("n=" + n);
  }

  /** @param n
   * @param one
   * @return matrix of dimensions n x n with multiplicative one on the diagonal
   * @throws Exception if n is negative or zero */
  public static Tensor of(int n, Scalar one) {
    if (0 < n)
      return Tensors.matrix((i, j) -> i.equals(j) ? one : one.zero(), n, n);
    throw new RuntimeException("n=" + n);
  }
}
