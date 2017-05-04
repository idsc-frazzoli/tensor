// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/RowReduce.html">RowReduce</a> */
public enum RowReduce {
  ;
  /** @param matrix
   * @return */
  public static Tensor of(Tensor matrix) {
    return new GaussianElimination(matrix, Pivot.argMaxAbs).lhs;
  }
}
