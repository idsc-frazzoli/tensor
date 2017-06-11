// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.sca.Conjugate;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/LeastSquares.html">LeastSquares</a> */
public enum LeastSquares {
  ;
  /** @param m is matrix with rows >= cols, and maximum rank
   * @param b
   * @return x with m.x ~ b */
  public static Tensor of(Tensor m, Tensor b) {
    Tensor mt = Transpose.of(Conjugate.of(m)); // TODO give reference for use of conjugate() for least squares?
    return LinearSolve.of(mt.dot(m), mt.dot(b));
  }

  /** for numerical stability
   * function usingSvd(...) is preferred over of(...)
   * 
   * @param m is matrix with rows >= cols
   * @param b
   * @return with m.x ~ b */
  public static Tensor usingSvd(Tensor m, Tensor b) {
    return PseudoInverse.of(m).dot(b);
  }
}
