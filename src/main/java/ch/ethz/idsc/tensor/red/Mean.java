// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Mean.html">Mean</a> */
public class Mean {
  /** @param tensor
   * @return average of entries in tensor */
  public static Tensor of(Tensor tensor) {
    return Total.of(tensor).multiply(RationalScalar.of(1, tensor.length()));
  }
}
