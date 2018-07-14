// code by jph
// https://stats.stackexchange.com/questions/66088/analysis-with-complex-data-anything-different
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/LeastSquares.html">LeastSquares</a> */
public enum LeastSquares {
  ;
  /** @param m is matrix with rows >= cols, and maximum rank
   * @param b
   * @return x with m.x ~ b */
  public static Tensor of(Tensor m, Tensor b) {
    Tensor mt = ConjugateTranspose.of(m);
    return LinearSolve.of(mt.dot(m), mt.dot(b));
  }

  /** for numerical stability
   * function usingSvd(...) is preferred over of(...)
   * 
   * @param m is matrix with rows >= cols
   * @param b
   * @return x with m.x ~ b */
  public static Tensor usingSvd(Tensor m, Tensor b) {
    return PseudoInverse.of(m).dot(b);
  }
}
