// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Tensor;

/* package */ class AbstractReduce {
  /** access the unpermuted lhs via function lhs() */
  final Tensor lhs;
  final Pivot pivot;
  final int n;
  final int[] ind;
  private int transpositions = 0;

  /** constructor only to be called from {@link GaussianElimination} */
  AbstractReduce(Tensor matrix, Pivot pivot) {
    lhs = matrix.copy();
    this.pivot = pivot;
    n = lhs.length();
    ind = IntStream.range(0, n).toArray();
  }

  final void swap(int k, int c0) {
    if (k != c0) {
      ++transpositions;
      int swap = ind[k];
      ind[k] = ind[c0];
      ind[c0] = swap;
    }
  }

  /** @return lhs */
  final Tensor lhs() {
    return Tensor.of(IntStream.of(ind).mapToObj(lhs::get));
  }

  final int transpositions() {
    return transpositions;
  }
}
