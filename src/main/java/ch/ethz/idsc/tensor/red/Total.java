// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ZeroScalar;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Total.html">Total</a> */
public class Total {
  /** @return total of list entries */
  public static Tensor of(Tensor tensor) {
    return tensor.flatten(0) //
        .parallel() //
        .reduce(Tensor::add) //
        .orElse(ZeroScalar.get());
  }
}
