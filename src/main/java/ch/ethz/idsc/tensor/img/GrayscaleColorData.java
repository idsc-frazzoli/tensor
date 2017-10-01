// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.NumberQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.N;

/* package */ enum GrayscaleColorData implements ColorDataFunction {
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
    return NumberQ.of(scalar) ? //
        tensors[(int) (scalar.number().doubleValue() * 255 + 0.5)] : ColorDataFunction.transparent();
  }
}
