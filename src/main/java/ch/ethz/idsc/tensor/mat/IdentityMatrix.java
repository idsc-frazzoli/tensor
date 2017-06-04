// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.red.KroneckerDelta;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/IdentityMatrix.html">IdentityMatrix</a> */
public enum IdentityMatrix {
  ;
  /** @param n
   * @return identity matrix of dimensions n x n */
  public static Tensor of(int n) {
    return Tensors.matrix(KroneckerDelta::of, n, n);
  }

  /** @param n
   * @param one
   * @return matrix of dimensions n x n with multiplicative one on the diagonal */
  public static Tensor of(int n, Scalar one) {
    return Tensors.matrix((i, j) -> i.equals(j) ? one : one.zero(), n, n);
  }
}
