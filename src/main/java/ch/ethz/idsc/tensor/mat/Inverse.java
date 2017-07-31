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
    return of(m, IdentityMatrix.of(m.length()));
  }

  /** @param m square matrix
   * @return inverse of m */
  public static final Tensor of(Tensor m, Tensor identity) {
    return LinearSolve.of(m, identity);
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
   * @return inverse of m with respect to identity */
  public static final Tensor withoutAbs(Tensor m, Tensor identity) {
    return LinearSolve.withoutAbs(m, identity);
  }
}
