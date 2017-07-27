// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.MachineNumberQ;
import ch.ethz.idsc.tensor.Tensor;

enum StaticHelper {
  ;
  /** @param tensor
   * @return true if any scalar in tensor evaluates MachineNumberQ as true */
  static boolean anyMachineNumberQ(Tensor tensor) {
    return tensor.flatten(-1).anyMatch(MachineNumberQ::of);
  }
}
