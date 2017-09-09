// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;

/* package */ interface Pivot {
  /** @param c0 row
   * @param j fixed column
   * @param ind permutation
   * @param lhs matrix
   * @return row index between c0 and ind.length that should be used as pivot element
   * if all pivot candidates are 0, the function returns c0 */
  int get(int c0, int j, int[] ind, Tensor lhs);
}