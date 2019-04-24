// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;

/* package */ class Determinant extends AbstractReduce {
  /** @param matrix square
   * @param pivot
   * @return determinant of given matrix */
  public static Scalar of(Tensor matrix, Pivot pivot) {
    return new Determinant(matrix, pivot).solve();
  }

  // ---
  private Determinant(Tensor matrix, Pivot pivot) {
    super(matrix, pivot);
  }

  /** @return determinant */
  private Scalar solve() {
    for (int c0 = 0; c0 < n; ++c0) {
      swap(pivot.get(c0, c0, ind, lhs), c0);
      Scalar piv = lhs.Get(ind[c0], c0);
      if (Scalars.isZero(piv))
        return piv;
      eliminate(c0, piv);
    }
    Scalar scalar = IntStream.range(0, lhs.length()) //
        .mapToObj(c0 -> lhs.Get(ind[c0], c0)) //
        .reduce(Scalar::multiply) //
        .get();
    return transpositions() % 2 == 0 //
        ? scalar
        : scalar.negate();
  }

  private void eliminate(int c0, Scalar piv) {
    IntStream.range(c0 + 1, lhs.length()).forEach(c1 -> { // deliberately without parallel
      Scalar fac = lhs.Get(ind[c1], c0).divide(piv).negate();
      lhs.set(lhs.get(ind[c1]).add(lhs.get(ind[c0]).multiply(fac)), ind[c1]);
    });
  }
}
