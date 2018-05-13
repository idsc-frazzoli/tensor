// code by jph
package ch.ethz.idsc.tensor.img;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

import ch.ethz.idsc.tensor.NumberQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Floor;

/** API still under evaluation */
public class SimpleColorDataIndexed implements ColorDataIndexed {
  /** @param tensor with dimensions N x 4 where each row encodes {R, G, B, A}
   * @return */
  public static ColorDataIndexed create(Tensor tensor) {
    return new SimpleColorDataIndexed(tensor.copy());
  }

  /** matrix with dimensions N x 4 */
  private final Tensor tensor;
  private final List<Color> list;

  /* package */ SimpleColorDataIndexed(Tensor tensor) {
    this.tensor = tensor;
    list = tensor.stream().map(ColorFormat::toColor).collect(Collectors.toList());
  }

  @Override
  public Tensor apply(Scalar scalar) {
    return NumberQ.of(scalar) //
        ? tensor.get(Floor.FUNCTION.apply(scalar).number().intValue())
        : StaticHelper.transparent();
  }

  @Override // from ColorDataIndexed
  public Color getColor(int index) {
    return list.get(index);
  }

  @Override // from ColorDataIndexed
  public int size() {
    return list.size();
  }

  @Override
  public ColorDataIndexed deriveWithAlpha(int alpha) {
    Tensor tensor = this.tensor.copy();
    Scalar scalar = RealScalar.of(alpha);
    tensor.set(entry -> scalar, Tensor.ALL, 3);
    return new SimpleColorDataIndexed(tensor);
  }
}
