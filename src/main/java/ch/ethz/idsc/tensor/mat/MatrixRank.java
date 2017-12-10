// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Unprotect;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.sca.Chop;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/MatrixRank.html">MatrixRank</a> */
public enum MatrixRank {
  ;
  /** if the matrix contains only exact precision entries,
   * the method {@link #usingRowReduce(Tensor)} is used,
   * otherwise {@link #usingSvd(Tensor)} is used.
   * 
   * @param matrix with exact and/or numeric precision entries
   * @return rank of matrix */
  public static int of(Tensor matrix) {
    return ExactScalarQ.all(matrix) ? usingRowReduce(matrix) : usingSvd(matrix);
  }

  /** @param matrix with exact precision entries
   * @return rank of matrix */
  public static int usingRowReduce(Tensor matrix) {
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

  /** @param svd
   * @param chop threshold
   * @return rank of matrix decomposed in svd */
  public static int of(SingularValueDecomposition svd, Chop chop) {
    return Math.toIntExact(svd.values().stream() //
        .map(Scalar.class::cast).map(chop).filter(Scalars::nonZero).count());
  }

  /** @param svd
   * @return rank of matrix decomposed in svd */
  public static int of(SingularValueDecomposition svd) {
    return of(svd, NullSpace.CHOP_DEFAULT);
  }
}
