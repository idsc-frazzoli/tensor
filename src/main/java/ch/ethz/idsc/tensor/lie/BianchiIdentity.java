// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.red.Total;

public class BianchiIdentity {
  public static Tensor of(Tensor rie) {
    return Total.of(Tensors.of( //
        Transpose.of(rie, 0, 1, 2, 3), // identity
        Transpose.of(rie, 0, 2, 3, 1), //
        Transpose.of(rie, 0, 3, 1, 2)));
  }
}
