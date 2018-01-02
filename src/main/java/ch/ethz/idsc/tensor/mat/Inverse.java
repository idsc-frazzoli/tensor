// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Inverse.html">Inverse</a> */
public enum Inverse {
  ;
  /** @param matrix with square dimensions
   * @return inverse of given matrix */
  public static Tensor of(Tensor matrix) {
    return LinearSolve.of(matrix, IdentityMatrix.of(matrix.length()));
  }

  /** function doesn't invoke Scalar::abs but pivots at the first non-zero column entry
   * 
   * @param matrix with square dimensions
   * @return */
  public static Tensor withoutAbs(Tensor matrix) {
    return LinearSolve.withoutAbs(matrix, IdentityMatrix.of(matrix.length()));
  }
}
