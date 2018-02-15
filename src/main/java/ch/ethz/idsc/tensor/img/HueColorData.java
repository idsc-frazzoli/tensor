// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.opt.ScalarTensorFunction;

/* package */ enum HueColorData implements ScalarTensorFunction {
  FUNCTION;
  // ---
  @Override
  public Tensor apply(Scalar scalar) {
    double value = scalar.number().doubleValue();
    return Double.isFinite(value) //
        ? ColorFormat.toVector(Hue.of(value, 1, 1, 1))
        : StaticHelper.transparent();
  }
}
