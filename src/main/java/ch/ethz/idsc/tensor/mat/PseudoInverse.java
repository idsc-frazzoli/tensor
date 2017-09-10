// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.function.Function;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Unprotect;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.InvertUnlessZero;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/PseudoInverse.html">PseudoInverse</a> */
public enum PseudoInverse {
  ;
  /** @param matrix
   * @return pseudoinverse of matrix */
  public static Tensor of(Tensor matrix) {
    return Unprotect.dimension1(matrix) <= matrix.length() ? //
        of(SingularValueDecomposition.of(matrix)) : //
        Transpose.of(of(Transpose.of(matrix)));
  }

  /** @param svd
   * @return pseudoinverse of matrix determined by given svd */
  public static Tensor of(SingularValueDecomposition svd) {
    double w_threshold = svd.getThreshold();
    Tensor wi = svd.values().map(PseudoInverse.orInvert(w_threshold));
    return Tensor.of(svd.getV().stream().map(row -> row.pmul(wi))).dot(Transpose.of(svd.getU()));
  }

  /** @return chop(scalar) == zero ? zero : scalar.reciprocal() */
  /* package */ static ScalarUnaryOperator orInvert(double threshold) {
    Function<Scalar, Scalar> function = InvertUnlessZero.FUNCTION.compose(Chop.below(threshold));
    return scalar -> function.apply(scalar);
  }
}
