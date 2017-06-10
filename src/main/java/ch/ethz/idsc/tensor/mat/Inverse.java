// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Inverse.html">Inverse</a> */
public enum Inverse {
  ;
  /** @param m square matrix
   * @return inverse of m */
  public static final Tensor of(Tensor m) {
    return LinearSolve.of(m, IdentityMatrix.of(m.length()));
  }

  /** doesn't require Scalar::abs
   * 
   * @param m
   * @return */
  public static final Tensor withoutAbs(Tensor m) {
    return LinearSolve.withoutAbs(m, IdentityMatrix.of(m.length()));
  }

  /** doesn't require Scalar::abs
   *
   * @param m
   * @param identity
   * @return inverse of m with respect to identity */
  @Deprecated // directly use LinearSolve.withoutAbs(m, identity);
  public static final Tensor withoutAbs(Tensor m, Tensor identity) {
    return LinearSolve.withoutAbs(m, identity);
  }
}
