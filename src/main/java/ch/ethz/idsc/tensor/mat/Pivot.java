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
    public int get(int c0, int[] ind, Tensor lhs) {
      return c0 + ArgMax.of( //
          Tensor.of(IntStream.range(c0, ind.length).boxed() //
              .map(c1 -> lhs.Get(ind[c1], c0).abs())));
    }
  };
  /** picks the first non Zero element in the column as pivot
   * throws an exception if no such element is found */
  static final Pivot firstNonZero = new Pivot() {
    @Override
    public int get(int c0, int[] ind, Tensor lhs) {
      return IntStream.range(c0, ind.length) //
          .filter(c1 -> !lhs.Get(ind[c1], c0).equals(ZeroScalar.get())) //
          .findFirst().getAsInt();
    }
  };

  /** @param c0 row and column offset
   * @param ind permutation
   * @param lhs matrix
   * @return row index between c0 and ind.length that should be used as pivot element */
  int get(int c0, int[] ind, Tensor lhs);
}