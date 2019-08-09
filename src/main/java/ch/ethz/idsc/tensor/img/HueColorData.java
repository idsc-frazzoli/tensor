// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Clips;

/* package */ class HueColorData implements ColorDataGradient {
  public static final ColorDataGradient DEFAULT = new HueColorData(1.0);
  // ---
  private final double opacity;

  private HueColorData(double opacity) {
    this.opacity = opacity;
  }

  @Override // from ScalarTensorFunction
  public Tensor apply(Scalar scalar) {
    double value = scalar.number().doubleValue();
    return Double.isFinite(value) //
        ? ColorFormat.toVector(Hue.of(value, 1, 1, opacity))
        : Transparent.rgba();
  }

  @Override // from ColorDataGradient
  public ColorDataGradient deriveWithOpacity(Scalar opacity) {
    return new HueColorData(Clips.unit().requireInside(opacity).number().doubleValue());
  }
}
