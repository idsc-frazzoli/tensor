// code by jph
package ch.ethz.idsc.tensor.img;

import java.awt.Color;

import ch.ethz.idsc.tensor.NumberQ;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/* package */ abstract class BaseColorDataIndexed implements ColorDataIndexed {
  private final Tensor tensor;
  protected final Color[] colors;
  private final int resolution;

  protected BaseColorDataIndexed(Tensor tensor) {
    this.tensor = tensor;
    colors = tensor.stream().map(ColorFormat::toColor).toArray(Color[]::new);
    resolution = colors.length - 1;
  }

  @Override // from ScalarTensorFunction
  public final Tensor apply(Scalar scalar) {
    return NumberQ.of(scalar) ? tensor.get(toInt(scalar)) : StaticHelper.transparent();
  }

  @Override // from ColorDataIndexed
  public final Color rescaled(double value) {
    return getColor((int) (value * resolution));
  }

  /** @param scalar
   * @return */
  protected abstract int toInt(Scalar scalar);

  protected final Tensor tableWithAlpha(int alpha) {
    return Tensor.of(tensor.stream().map(StaticHelper.withAlpha(alpha)));
  }
}
