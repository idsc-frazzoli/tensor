// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.sca.Chop;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/PseudoInverse.html">PseudoInverse</a> */
public class PseudoInverse {
  // TODO what if rows<cols?
  /** @param m
   * @return pseudoinverse of m */
  public static Tensor of(Tensor m) {
    return of(SingularValueDecomposition.of(m));
  }

  public static Tensor of(SingularValueDecomposition svd) {
    double w_threshold = svd.getThreshold();
    Tensor wi = svd.getW().map(Chop.orInvert(w_threshold));
    return Tensor.of(svd.getV().flatten(0).map(row -> row.pmul(wi))).dot(Transpose.of(svd.getU()));
  }
}
