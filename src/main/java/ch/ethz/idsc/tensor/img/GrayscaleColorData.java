// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.opt.ScalarTensorFunction;
import ch.ethz.idsc.tensor.sca.N;

/* package */ enum GrayscaleColorData implements ScalarTensorFunction {
  FUNCTION;
  // ---
  private final Tensor[] tensors = new Tensor[256];

  private GrayscaleColorData() {
    Scalar TFF = RealScalar.of(255);
    for (int index = 0; index < 256; ++index) {
      Scalar num = RealScalar.of(index);
      tensors[index] = N.DOUBLE.of(Tensors.of(num, num, num, TFF));
    }
  }

  @Override
  public Tensor apply(Scalar scalar) {
    double value = scalar.number().doubleValue();
    return Double.isFinite(value) //
        ? tensors[(int) (value * 255 + 0.5)]
        : StaticHelper.transparent();
  }
}
