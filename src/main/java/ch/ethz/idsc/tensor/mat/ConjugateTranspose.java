// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.sca.Conjugate;

/** implementation consistent with Mathematica.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ConjugateTranspose.html">ConjugateTranspose</a> */
public enum ConjugateTranspose {
  ;
  /** @param tensor of rank at least 2
   * @return transpose of tensor with entries conjugated
   * @throws Exception if given tensor is not of rank at least 2
   * @see Conjugate */
  public static Tensor of(Tensor tensor) {
    return Conjugate.of(Transpose.of(tensor));
  }
}
