// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.sca.Chop;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/PseudoInverse.html">PseudoInverse</a> */
public enum PseudoInverse {
  ;
  /** @param matrix
   * @return pseudoinverse of matrix */
  public static Tensor of(Tensor matrix) {
    return matrix.get(0).length() <= matrix.length() ? //
        of(SingularValueDecomposition.of(matrix)) : //
        Transpose.of(of(Transpose.of(matrix)));
  }

  /** @param svd
   * @return pseudoinverse of matrix determined by given svd */
  public static Tensor of(SingularValueDecomposition svd) {
    double w_threshold = svd.getThreshold();
    Tensor wi = svd.getW().map(Chop.orInvert(w_threshold));
    return Tensor.of(svd.getV().flatten(0).map(row -> row.pmul(wi))).dot(Transpose.of(svd.getU()));
  }
}
