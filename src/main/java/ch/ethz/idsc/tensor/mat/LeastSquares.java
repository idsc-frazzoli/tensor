// code by jph
// https://stats.stackexchange.com/questions/66088/analysis-with-complex-data-anything-different
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/LeastSquares.html">LeastSquares</a> */
public enum LeastSquares {
  ;
  /** @param matrix with rows >= cols, and maximum rank
   * @param b
   * @return x with matrix.dot(x) ~ b
   * @throws Exception if matrix does not have maximum rank */
  public static Tensor of(Tensor matrix, Tensor b) {
    Tensor mt = ConjugateTranspose.of(matrix);
    return LinearSolve.of(mt.dot(matrix), mt.dot(b));
  }

  /** when m does not have full rank, and for numerical stability
   * the function usingSvd(...) is preferred over the function of(...)
   * 
   * @param matrix with rows >= cols
   * @param b
   * @return x with matrix.dot(x) ~ b */
  public static Tensor usingSvd(Tensor matrix, Tensor b) {
    return PseudoInverse.of(matrix).dot(b);
  }
}
