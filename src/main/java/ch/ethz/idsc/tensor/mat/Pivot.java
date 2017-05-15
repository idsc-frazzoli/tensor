// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ZeroScalar;
import ch.ethz.idsc.tensor.red.ArgMax;

/* package */ interface Pivot {
  /** selects entry with largest absolute value */
  static final Pivot argMaxAbs = new Pivot() {
    @Override
    public int get(int c0, int j, int[] ind, Tensor lhs) {
      return c0 + ArgMax.of( //
          Tensor.of(IntStream.range(c0, ind.length).boxed() //
              .map(c1 -> lhs.Get(ind[c1], j).abs())));
    }
  };
  /** picks the first non Zero element in the column as pivot
   * the return value is c0 in the case when the element at (ind[c0],j)
   * is non-zero, but also if none of the candidates is non-zero */
  static final Pivot firstNonZero = new Pivot() {
    @Override
    public int get(int c0, int j, int[] ind, Tensor lhs) {
      return IntStream.range(c0, ind.length) //
          .filter(c1 -> !lhs.Get(ind[c1], j).equals(ZeroScalar.get())) //
          .findFirst().orElse(c0);
    }
  };

  /** @param c0 row
   * @param j fixed column
   * @param ind permutation
   * @param lhs matrix
   * @return row index between c0 and ind.length that should be used as pivot element
   * if all pivot candidates are 0, the function returns c0 */
  int get(int c0, int j, int[] ind, Tensor lhs);
}