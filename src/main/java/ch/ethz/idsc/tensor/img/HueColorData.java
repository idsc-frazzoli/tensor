// code from Computer Graphics, by Donald Hearn and Pauline Baker
// adapted by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.NumberQ;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/* package */ enum HueColorData implements ColorDataFunction {
  FUNCTION;
  // ---
  @Override
  public Tensor apply(Scalar scalar) {
    return NumberQ.of(scalar) ? //
        ColorFormat.toVector(Hue.of(scalar.number().doubleValue(), 1, 1, 1)) : ColorDataFunction.transparent();
  }
}
