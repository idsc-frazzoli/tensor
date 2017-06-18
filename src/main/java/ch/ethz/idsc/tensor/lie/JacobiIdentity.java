// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Tensor;

public enum JacobiIdentity {
  ;
  /** @param ad tensor of Lie-algebra
   * @return 4-dimensional array of all zeros if ad corresponds to a Lie-algebra */
  public static Tensor of(Tensor ad) {
    return BianchiIdentity.of(ad.dot(ad));
  }
}
