// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/* package */ enum FallbackColorDataGradient implements ColorDataGradient {
  INSTANCE;
  // ---
  @Override // from ColorDataGradient
  public Tensor apply(Scalar scalar) {
    return StaticHelper.transparent();
  }

  @Override // from ColorDataGradient
  public ColorDataGradient deriveWithFactor(Scalar factor) {
    return this;
  }
}
