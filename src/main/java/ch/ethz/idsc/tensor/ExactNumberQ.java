// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.sca.ExactNumberQInterface;

/** implementation consistent with Mathematica
 * 
 * see also {@link MachineNumberQ}
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ExactNumberQ.html">ExactNumberQ</a> */
public enum ExactNumberQ {
  ;
  /** @param tensor
   * @return true, if tensor is instance of {@link ExactNumberQInterface} which evaluates to true */
  public static boolean of(Tensor tensor) {
    if (tensor instanceof ExactNumberQInterface) {
      ExactNumberQInterface exactNumberQInterface = (ExactNumberQInterface) tensor;
      return exactNumberQInterface.isExactNumber();
    }
    return false;
  }

  /** @param tensor
   * @return true, if all scalar entries in given tensor satisfy {@link ExactNumberQ} predicate */
  public static boolean all(Tensor tensor) {
    return tensor.flatten(-1).allMatch(ExactNumberQ::of);
  }
}
