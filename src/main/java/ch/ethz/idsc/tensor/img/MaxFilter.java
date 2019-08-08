// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.Entrywise;

/** <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/MaxFilter.html">MaxFilter</a> */
public enum MaxFilter {
  ;
  /** @param tensor
   * @param radius
   * @return */
  public static Tensor of(Tensor tensor, int radius) {
    return TensorExtract.of(tensor, radius, Entrywise.max()::of);
  }
}
