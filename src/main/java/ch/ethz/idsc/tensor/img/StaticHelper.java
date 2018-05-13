// code by gjoel and jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;

/* package */ enum StaticHelper {
  ;
  /* package */ static final ColorDataGradient FALLBACK = ColorDataGradient.of(Array.zeros(2, 4));
  // ---
  private static final Tensor TRANSPARENT = Tensors.vectorDouble(0, 0, 0, 0).unmodifiable();

  /* package */ static Tensor transparent() {
    return TRANSPARENT.copy();
  }

  static String colorlist(String name) {
    if (name.charAt(0) == '_')
      name = name.substring(1);
    return "/colorlist/" + name.toLowerCase() + ".csv";
  }
}
