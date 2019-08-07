// code by gjoel and jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Transparent.html">Transparent</a> */
/* package */ enum Transparent {
  ;
  private static final Tensor RGBA = Tensors.vectorDouble(0, 0, 0, 0).unmodifiable();

  /** @return {0.0, 0.0, 0.0, 0.0} */
  public static Tensor rgba() {
    return RGBA.copy();
  }
}
