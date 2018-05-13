// code by jph
package ch.ethz.idsc.tensor.img;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

import ch.ethz.idsc.tensor.NumberQ;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Floor;

/** API still under evaluation */
public class SimpleColorDataList implements ColorDataIndexed {
  /** @param tensor with dimensions N x 4 where each row encodes {R, G, B, A}
   * @return */
  public static ColorDataIndexed create(Tensor tensor) {
    return new SimpleColorDataList(tensor.copy());
  }

  /** matrix with dimensions N x 4 */
  private final Tensor tensor;
  private final List<Color> list;

  SimpleColorDataList(Tensor tensor) {
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
}
