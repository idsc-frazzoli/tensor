// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.Clips;
import ch.ethz.idsc.tensor.sca.N;

/* package */ class GrayscaleColorData implements ColorDataGradient {
  public static final ColorDataGradient DEFAULT = new GrayscaleColorData(RealScalar.of(255));
  // ---
  private final Tensor[] tensors = new Tensor[256];

  private GrayscaleColorData(Scalar alpha) {
    for (int index = 0; index < 256; ++index) {
      Scalar num = RealScalar.of(index);
      tensors[index] = N.DOUBLE.of(Tensors.of(num, num, num, alpha));
    }
  }

  @Override // from ScalarTensorFunction
  public Tensor apply(Scalar scalar) {
    double value = scalar.number().doubleValue();
    return Double.isFinite(value) //
        ? tensors[(int) (value * 255 + 0.5)]
        : StaticHelper.transparent();
  }

  @Override // from ColorDataGradient
  public ColorDataGradient deriveWithOpacity(Scalar opacity) {
    double value = Clips.unit().requireInside(opacity).number().doubleValue();
    return new GrayscaleColorData(RealScalar.of((int) (value * 255 + 0.5)));
  }
}
