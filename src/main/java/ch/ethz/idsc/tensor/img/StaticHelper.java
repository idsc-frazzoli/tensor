// code by gjoel and jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/* package */ enum StaticHelper {
  ;
  private static final Tensor TRANSPARENT = Tensors.vectorDouble(0, 0, 0, 0).unmodifiable();

  /* package */ static Tensor transparent() {
    return TRANSPARENT.copy();
  }
}
