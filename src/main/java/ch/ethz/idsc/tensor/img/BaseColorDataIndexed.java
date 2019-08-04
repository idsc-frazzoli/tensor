// code by jph
package ch.ethz.idsc.tensor.img;

import java.awt.Color;

import ch.ethz.idsc.tensor.NumberQ;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/* package */ abstract class BaseColorDataIndexed implements ColorDataIndexed {
  private final Tensor tensor;
  protected final Color[] colors;

  protected BaseColorDataIndexed(Tensor tensor) {
    this.tensor = tensor;
    colors = tensor.stream() //
        .map(ColorFormat::toColor) //
        .toArray(Color[]::new);
  }

  @Override // from ScalarTensorFunction
  public final Tensor apply(Scalar scalar) {
    return NumberQ.of(scalar) //
        ? tensor.get(toInt(scalar))
        : StaticHelper.transparent();
  }

  @Override // from ColorDataIndexed
  public final int length() {
    return colors.length;
  }

  /** @param scalar
   * @return */
  protected abstract int toInt(Scalar scalar);

  /** @param alpha in the range [0, 1, ..., 255]
   * @return */
  protected final Tensor tableWithAlpha(int alpha) {
    return StaticHelper.withAlpha(tensor, alpha);
  }
}
