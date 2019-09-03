// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.mat.SquareMatrixQ;
import ch.ethz.idsc.tensor.red.Trace;

/** https://en.wikipedia.org/wiki/Killing_form */
public enum KillingForm {
  ;
  /** @param ad tensor of Lie-algebra
   * @return Killing-form of Lie-algebra
   * @throws Exception if rank of ad is not 3 */
  public static Tensor of(Tensor ad) {
    return SquareMatrixQ.require(Trace.of(ad.dot(ad), 0, 3));
  }
}
