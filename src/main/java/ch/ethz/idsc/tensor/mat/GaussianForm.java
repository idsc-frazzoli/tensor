// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Unprotect;

/* package */ class GaussianForm {
  /** @param matrix
   * @param pivot
   * @return reduced row echelon form (also called row canonical form) */
  static Tensor rowReduce(Tensor matrix, Pivot pivot) {
    GaussianForm gaussianForm = new GaussianForm(matrix, pivot);
    gaussianForm.solve();
    return gaussianForm.lhs();
  }

  /** access the unpermuted lhs via function lhs() */
  final Tensor lhs;
  private final Pivot pivot;
  final int n;
  final int[] ind;
  private int transpositions = 0;

  /** constructor only to be called from {@link GaussianElimination} */
  GaussianForm(Tensor matrix, Pivot pivot) {
    lhs = matrix.copy();
    this.pivot = pivot;
    n = lhs.length();
    ind = IntStream.range(0, n).toArray();
  }

  private void solve() {
    int m = Unprotect.dimension1(lhs);
    int j = 0;
    for (int c0 = 0; c0 < n && j < m; ++j) {
      swap(pivot.get(c0, j, ind, lhs), c0);
      Scalar piv = lhs.Get(ind[c0], j);
      if (Scalars.nonZero(piv)) {
        for (int c1 = 0; c1 < n; ++c1)
          if (c1 != c0) {
            Scalar fac = lhs.Get(ind[c1], j).divide(piv).negate();
            lhs.set(lhs.get(ind[c1]).add(lhs.get(ind[c0]).multiply(fac)), ind[c1]);
          }
        lhs.set(lhs.get(ind[c0]).divide(piv), ind[c0]);
        ++c0;
      }
    }
  }

  void swap(int k, int c0) {
    if (k != c0) {
      ++transpositions;
      int swap = ind[k];
      ind[k] = ind[c0];
      ind[c0] = swap;
    }
  }

  /** @return lhs */
  Tensor lhs() {
    return Tensor.of(IntStream.of(ind).mapToObj(lhs::get));
  }

  /** @return determinant */
  Scalar det() {
    Scalar scalar = IntStream.range(0, lhs.length()) //
        .mapToObj(c0 -> lhs.Get(ind[c0], c0)) //
        .reduce(Scalar::multiply) //
        .get();
    return transpositions % 2 == 0 //
        ? scalar
        : scalar.negate();
  }
}
