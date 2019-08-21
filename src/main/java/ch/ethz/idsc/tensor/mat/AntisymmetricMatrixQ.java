// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.sca.Chop;

/** @see HermitianMatrixQ
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/AntisymmetricMatrixQ.html">AntisymmetricMatrixQ</a> */
public enum AntisymmetricMatrixQ {
  ;
  /** @param tensor
   * @param chop
   * @return true if tensor is an anti-symmetric matrix */
  public static boolean of(Tensor tensor, Chop chop) {
    return StaticHelper.addId(tensor, chop, matrix -> Transpose.of(matrix).negate());
  }

  /** @param tensor
   * @return true if tensor is an anti-symmetric matrix */
  public static boolean of(Tensor tensor) {
    return of(tensor, Tolerance.CHOP);
  }
}
