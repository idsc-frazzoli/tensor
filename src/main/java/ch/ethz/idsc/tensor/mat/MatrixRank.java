// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/MatrixRank.html">MatrixRank</a> */
public enum MatrixRank {
  ;
  /** @return rank of matrix m */
  public static int of(Tensor m) {
    // TODO use row echelon
    return of(SingularValueDecomposition.of(m));
  }

  /** @return rank of matrix m */
  public static int usingSvd(Tensor m) {
    return of(SingularValueDecomposition.of(m));
  }

  /** @return rank of matrix decomposed in svd */
  public static int of(SingularValueDecomposition svd) {
    double w_threshold = svd.getThreshold();
    return (int) svd.getW().flatten(0).map(Scalar.class::cast) //
        .filter(value -> w_threshold <= value.abs().number().doubleValue()) //
        .count();
  }
}
