// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;
import java.util.List;

import ch.ethz.idsc.tensor.Tensor;

public enum ArrayPad {
  ;
  // ---
  public static Tensor of(Tensor tensor, Integer... pads) {
    return of(tensor, Arrays.asList(pads));
  }

  // TODO implementation
  public static Tensor of(Tensor tensor, List<Integer> pads) {
    // return tensor.flatten(0).map(t->Join.);
    return null;
  }
}
