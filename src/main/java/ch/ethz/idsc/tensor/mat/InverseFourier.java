// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Conjugate;

/** consistent with Mathematica for input vectors of length of power of 2
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/InverseFourier.html">InverseFourier</a> */
public enum InverseFourier {
  ;
  /** @param vector of length of power of 2
   * @return */
  public static Tensor of(Tensor vector) {
    return Conjugate.of(Fourier.of(Conjugate.of(vector)));
  }
}
