// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.sca.Chop;

/** consistent with Mathematica:
 * SymmetricMatrixQ[ {} ] == false
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/SymmetricMatrixQ.html">SymmetricMatrixQ</a> */
public enum SymmetricMatrixQ {
  ;
  /** @param tensor
   * @param chop
   * @return true if given tensor is a symmetric matrix */
  public static boolean of(Tensor tensor, Chop chop) {
    return StaticHelper.addId(tensor, chop, Transpose::of);
  }

  /** @param tensor
   * @return true if given tensor is a symmetric matrix */
  public static boolean of(Tensor tensor) {
    return of(tensor, Tolerance.CHOP);
  }
}
