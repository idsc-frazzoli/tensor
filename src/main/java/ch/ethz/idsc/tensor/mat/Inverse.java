// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Inverse.html">Inverse</a> */
public class Inverse {
  /** @param m square matrix
   * @return inverse of m */
  public static final Tensor of(Tensor m) {
    final int n = m.length();
    return LinearSolve.of(m, IdentityMatrix.of(n));
  }

  /** doesn't require Scalar::abs
   * 
   * @param m
   * @return */
  public static final Tensor withoutAbs(Tensor m) {
    return withoutAbs(m, IdentityMatrix.of(m.length()));
  }

  /** doesn't require Scalar::abs
   * 
   * @param m
   * @param identity
   * @return inverse of m relative to id */
  public static final Tensor withoutAbs(Tensor m, Tensor identity) {
    return LinearSolve.withoutAbs(m, identity);
  }
}
