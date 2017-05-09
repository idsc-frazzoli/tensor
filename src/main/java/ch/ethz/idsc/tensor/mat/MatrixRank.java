// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ZeroScalar;
import ch.ethz.idsc.tensor.alg.Transpose;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/MatrixRank.html">MatrixRank</a> */
public enum MatrixRank {
  ;
  /** @param matrix with exact precision entries
   * @return rank of matrix */
  public static int of(Tensor matrix) {
    int n = matrix.length();
    int m = matrix.get(0).length();
    Tensor lhs = RowReduce.of(matrix);
    int j = 0;
    int c0 = 0;
    while (j < n && c0 < m)
      if (!lhs.Get(j, c0++).equals(ZeroScalar.get())) // <- careful: c0 is modified
        ++j;
    return j;
  }

  /** @param matrix with numeric precision entries
   * @return rank of matrix */
  public static int usingSvd(Tensor matrix) {
    int n = matrix.length();
    int m = matrix.get(0).length();
    return of(SingularValueDecomposition.of(m <= n ? matrix : Transpose.of(matrix)));
  }

  /** @return rank of matrix decomposed in svd */
  public static int of(SingularValueDecomposition svd) {
    double w_threshold = svd.getThreshold();
    return (int) svd.getW().flatten(0).map(Scalar.class::cast) //
        .filter(value -> w_threshold <= value.abs().number().doubleValue()) //
        .count();
  }
}
