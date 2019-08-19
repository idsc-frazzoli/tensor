// code by jph
package ch.ethz.idsc.tensor.img;

import java.awt.Color;

import ch.ethz.idsc.tensor.NumberQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.opt.TensorUnaryOperator;

/* package */ abstract class BaseColorDataIndexed implements ColorDataIndexed {
  private final Tensor tensor;
  final Color[] colors;

  BaseColorDataIndexed(Tensor tensor) {
    this.tensor = tensor;
    colors = tensor.stream() //
        .map(ColorFormat::toColor) //
        .toArray(Color[]::new);
  }

  @Override // from ScalarTensorFunction
  public final Tensor apply(Scalar scalar) {
    return NumberQ.of(scalar) //
        ? tensor.get(toInt(scalar))
        : Transparent.rgba();
  }

  @Override // from ColorDataIndexed
  public final int length() {
    return colors.length;
  }

  /** @param scalar
   * @return */
  abstract int toInt(Scalar scalar);

  /** @param alpha in the range [0, 1, ..., 255]
   * @return */
  final Tensor tableWithAlpha(int alpha) {
    return Tensor.of(tensor.stream().map(withAlpha(alpha)));
  }

  /** @param alpha in the range [0, 1, ..., 255]
   * @return operator that maps a vector of the form {r, g, b, any} to {r, g, b, alpha} */
  private static TensorUnaryOperator withAlpha(int alpha) {
    Scalar scalar = RealScalar.of(alpha);
    return rgba -> rgba.extract(0, 3).append(scalar);
  }
}
