// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Chop;

/** Quote from Wikipedia: A Hermitian matrix (or self-adjoint matrix) is
 * a complex square matrix that is equal to its own conjugate transpose.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/HermitianMatrixQ.html">HermitianMatrixQ</a>
 * 
 * @see AntisymmetricMatrixQ */
public enum HermitianMatrixQ {
  ;
  /** @param tensor
   * @param chop
   * @return true if tensor is a Hermitian matrix */
  public static boolean of(Tensor tensor, Chop chop) {
    return StaticHelper.addId(tensor, chop, ConjugateTranspose::of);
  }

  /** @param tensor
   * @return true if tensor is a Hermitian matrix */
  public static boolean of(Tensor tensor) {
    return of(tensor, Tolerance.CHOP);
  }
}
