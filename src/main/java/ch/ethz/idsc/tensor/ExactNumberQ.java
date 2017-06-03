// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.sca.ExactNumberQInterface;

/** implementation consistent with Mathematica
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ExactNumberQ.html">ExactNumberQ</a> */
public enum ExactNumberQ {
  ;
  // ---
  /** @param tensor
   * @return true, if tensor is instance of {@link ExactNumberQInterface} which evaluates to true */
  public static boolean of(Tensor tensor) {
    if (tensor instanceof ExactNumberQInterface) {
      ExactNumberQInterface exactNumberQInterface = (ExactNumberQInterface) tensor;
      return exactNumberQInterface.isExactNumber();
    }
    return false;
  }
}
