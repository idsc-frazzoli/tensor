// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.Clips;

/* package */ class GrayscaleColorData implements ColorDataGradient {
  public static final ColorDataGradient DEFAULT = new GrayscaleColorData(255);
  // ---
  private final Tensor[] tensors = new Tensor[256];

  private GrayscaleColorData(int alpha) {
    for (int index = 0; index < 256; ++index)
      tensors[index] = Tensors.vectorDouble(index, index, index, alpha);
  }

  @Override // from ScalarTensorFunction
  public Tensor apply(Scalar scalar) {
    double value = scalar.number().doubleValue();
    return Double.isFinite(value) //
        ? tensors[(int) (value * 255 + 0.5)].copy()
        : Transparent.rgba();
  }

  @Override // from ColorDataGradient
  public ColorDataGradient deriveWithOpacity(Scalar opacity) {
    double value = Clips.unit().requireInside(opacity).number().doubleValue();
    return new GrayscaleColorData((int) (value * 255 + 0.5));
  }
}
