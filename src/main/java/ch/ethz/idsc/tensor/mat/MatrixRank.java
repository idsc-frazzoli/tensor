// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Unprotect;
import ch.ethz.idsc.tensor.alg.Transpose;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/MatrixRank.html">MatrixRank</a> */
public enum MatrixRank {
  ;
  /** @param matrix with exact precision entries
   * @return rank of matrix */
  public static int of(Tensor matrix) {
    int n = matrix.length();
    int m = Unprotect.dimension1(matrix);
    Tensor lhs = RowReduce.of(matrix);
    int j = 0;
    int c0 = 0;
    while (j < n && c0 < m)
      if (Scalars.nonZero(lhs.Get(j, c0++))) // <- careful: c0 is modified
        ++j;
    return j;
  }

  /** @param matrix with numeric precision entries
   * @return rank of matrix */
  public static int usingSvd(Tensor matrix) {
    int n = matrix.length();
    int m = Unprotect.dimension1(matrix);
    return of(SingularValueDecomposition.of(m <= n ? matrix : Transpose.of(matrix)));
  }

  /** @return rank of matrix decomposed in svd */
  public static int of(SingularValueDecomposition svd) {
    Scalar w_threshold = DoubleScalar.of(svd.getThreshold());
    return Math.toIntExact(svd.values().flatten(0) //
        .map(Scalar.class::cast) //
        .map(Scalar::abs) //
        .filter(value -> Scalars.lessEquals(w_threshold, value)) //
        .count());
  }
}
