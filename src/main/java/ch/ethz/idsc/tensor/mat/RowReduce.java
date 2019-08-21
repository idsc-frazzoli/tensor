// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/RowReduce.html">RowReduce</a>
 * 
 * @see LinearSolve
 * @see GaussianElimination */
public class RowReduce extends AbstractReduce {
  /** @param matrix
   * @return reduced row echelon form (also called row canonical form) of matrix */
  public static Tensor of(Tensor matrix) {
    return of(matrix, PivotArgMaxAbs.INSTANCE);
  }

  /** @param matrix
   * @return reduced row echelon form (also called row canonical form) of matrix */
  public static Tensor of(Tensor matrix, Pivot pivot) {
    return new RowReduce(matrix, pivot).solve();
  }

  // ---
  private RowReduce(Tensor matrix, Pivot pivot) {
    super(matrix, pivot);
  }

  private Tensor solve() {
    int m = Stream.of(lhs).mapToInt(Tensor::length).max().getAsInt();
    int j = 0;
    for (int c0 = 0; c0 < lhs.length && j < m; ++j) {
      swap(pivot.get(c0, j, ind, lhs), c0);
      Scalar piv = lhs[ind[c0]].Get(j);
      if (Scalars.nonZero(piv)) {
        for (int c1 = 0; c1 < lhs.length; ++c1)
          if (c1 != c0) {
            Scalar fac = lhs[ind[c1]].Get(j).divide(piv).negate();
            lhs[ind[c1]] = lhs[ind[c1]].add(lhs[ind[c0]].multiply(fac));
          }
        lhs[ind[c0]] = lhs[ind[c0]].divide(piv);
        ++c0;
      }
    }
    return Tensor.of(IntStream.of(ind).mapToObj(i -> lhs[i]));
  }
}
