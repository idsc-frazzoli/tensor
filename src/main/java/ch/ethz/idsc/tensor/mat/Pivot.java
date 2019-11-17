// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;

@FunctionalInterface
/* package */ interface Pivot {
  /** @param row
   * @param col fixed column
   * @param ind permutation
   * @param lhs matrix
   * @return row index between c0 and ind.length that should be used as pivot element
   * if all pivot candidates are 0, the function returns c0 */
  int get(int row, int col, int[] ind, Tensor[] lhs);
}