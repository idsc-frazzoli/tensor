// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.sca.Conjugate;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ConjugateTranspose.html">ConjugateTranspose</a> */
public enum ConjugateTranspose {
  ;
  /** @param matrix
   * @return transpose of matrix with entries conjugated
   * @see Conjugate */
  public static Tensor of(Tensor matrix) {
    return Conjugate.of(Transpose.of(matrix));
  }
}
