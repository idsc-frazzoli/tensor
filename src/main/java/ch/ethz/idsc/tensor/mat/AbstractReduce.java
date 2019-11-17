// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Tensor;

/** base class of {@link Determinant}, {@link GaussianElimination} and {@link RowReduce} */
/* package */ class AbstractReduce {
  final Tensor[] lhs;
  final Pivot pivot;
  final int[] ind;
  private int transpositions = 0;

  public AbstractReduce(Tensor matrix, Pivot pivot) {
    lhs = matrix.stream().map(Tensor::copy).toArray(Tensor[]::new);
    this.pivot = pivot;
    ind = IntStream.range(0, lhs.length).toArray();
  }

  final void swap(int k, int c0) {
    if (k != c0) {
      ++transpositions;
      int swap = ind[k];
      ind[k] = ind[c0];
      ind[c0] = swap;
    }
  }

  final int transpositions() {
    return transpositions;
  }
}
