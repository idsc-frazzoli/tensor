// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.Trace;

public class KillingForm {
  /** @param ad tensor of lie algebra
   * @return killing form of lie algebra */
  public static Tensor of(Tensor ad) {
    return Trace.of(ad.dot(ad), 0, 3);
  }
}
