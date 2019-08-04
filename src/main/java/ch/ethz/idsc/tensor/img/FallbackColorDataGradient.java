// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** fallback color data gradient is used when loading the
 * resource associated to a color data gradient fails. */
/* package */ enum FallbackColorDataGradient implements ColorDataGradient {
  INSTANCE;
  // ---
  @Override // from ColorDataGradient
  public Tensor apply(Scalar scalar) {
    return StaticHelper.transparent();
  }

  @Override // from ColorDataGradient
  public ColorDataGradient deriveWithOpacity(Scalar factor) {
    return this;
  }
}
