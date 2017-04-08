// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ZeroScalar;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Total.html">Total</a> */
public enum Total {
  ;
  /** @return total of list entries, or 0 if tensor is empty */
  public static Tensor of(Tensor tensor) {
    return Fold.parallel(Tensor::add, tensor).orElse(ZeroScalar.get());
  }

  /** @return total pointwise product of list entries, or 1 if tensor is empty */
  public static Tensor pmul(Tensor tensor) {
    return Fold.parallel(Tensor::pmul, tensor).orElse(RealScalar.ONE);
  }
}
