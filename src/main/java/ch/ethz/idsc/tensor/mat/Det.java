// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Array;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Det.html">Det</a> */
public enum Det {
  ;
  /** @param m square matrix
   * @return determinant of m */
  public static Scalar of(Tensor m) {
    return new GaussianElimination(m, Array.zeros(m.length()), Pivot.argMaxAbs).det();
  }

  /** @param m square matrix
   * @return determinant of m */
  public static Scalar withoutAbs(Tensor m) {
    return new GaussianElimination(m, Array.zeros(m.length()), Pivot.firstNonZero).det();
  }
}
