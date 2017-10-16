// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.sca.MachineNumberQInterface;

/** implementation consistent with Mathematica
 * <pre>
 * MachineNumberQ[ 3.14 + 2.7*I ] == true
 * MachineNumberQ[ 13 / 17 ] == false
 * </pre>
 * 
 * <p>Special cases are
 * <pre>
 * MachineNumberQ[Infinity] == false
 * MachineNumberQ[Indeterminate] == false
 * </pre>
 * 
 * <p>see also {@link ExactNumberQ}
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/MachineNumberQ.html">MachineNumberQ</a> */
public enum MachineNumberQ {
  ;
  /** @param tensor
   * @return true, if tensor is instance of {@link MachineNumberQInterface} which evaluates to true,
   * otherwise false */
  public static boolean of(Tensor tensor) {
    if (tensor instanceof MachineNumberQInterface) {
      MachineNumberQInterface machineNumberQInterface = (MachineNumberQInterface) tensor;
      return machineNumberQInterface.isMachineNumber();
    }
    return false;
  }

  /** @param tensor
   * @return true, if any scalar entry in given tensor satisfies {@link MachineNumberQ} predicate */
  public static boolean any(Tensor tensor) {
    return tensor.flatten(-1).anyMatch(MachineNumberQ::of);
  }
}
