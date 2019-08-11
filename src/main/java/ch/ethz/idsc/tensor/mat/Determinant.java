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
    for (int c0 = 0; c0 < lhs.length; ++c0) {
      swap(pivot.get(c0, c0, ind, lhs), c0);
      Scalar piv = lhs[ind[c0]].Get(c0);
      if (Scalars.isZero(piv))
        return piv;
      eliminate(c0, piv);
    }
    Scalar scalar = IntStream.range(0, lhs.length) //
        .mapToObj(c0 -> lhs[ind[c0]].Get(c0)) //
        .reduce(Scalar::multiply) //
        .get();
    return transpositions() % 2 == 0 //
        ? scalar
        : scalar.negate();
  }

  private void eliminate(int c0, Scalar piv) {
    for (int c1 = c0 + 1; c1 < lhs.length; ++c1) { // deliberately without parallel
      int ic1 = ind[c1];
      Scalar fac = lhs[ic1].Get(c0).divide(piv).negate();
      lhs[ic1] = lhs[ic1].add(lhs[ind[c0]].multiply(fac));
    }
  }
}
