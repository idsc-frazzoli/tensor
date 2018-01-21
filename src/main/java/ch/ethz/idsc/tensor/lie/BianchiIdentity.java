// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Transpose;

public enum BianchiIdentity {
  ;
  /** @param rie tensor of rank 4
   * @return array of all-zeros if rie is a Riemannian-curvature tensor */
  public static Tensor of(Tensor rie) {
    return rie // == Transpose.of(rie, 0, 1, 2, 3) // identity
        .add(Transpose.of(rie, 0, 2, 3, 1)) //
        .add(Transpose.of(rie, 0, 3, 1, 2));
  }
}
