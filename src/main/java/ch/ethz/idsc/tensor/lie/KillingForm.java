// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.Trace;

/** https://en.wikipedia.org/wiki/Killing_form */
public enum KillingForm {
  ;
  /** @param ad tensor of Lie-algebra
   * @return Killing-form of Lie-algebra */
  public static Tensor of(Tensor ad) {
    return Trace.of(ad.dot(ad), 0, 3);
  }
}
