// code by jph
package ch.ethz.idsc.tensor.img;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

import ch.ethz.idsc.tensor.NumberQ;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.io.ResourceData;
import ch.ethz.idsc.tensor.sca.Floor;

public enum ColorDataLists implements ColorDataIndexed {
  _001, //
  _003, //
  _058, //
  _061, //
  _063, //
  _074, //
  _094, //
  _096, //
  /** mathematica default */
  _097, //
  _098, //
  _099, //
  _100, //
  ;
  private final Tensor tensor;
  private final List<Color> list;

  private ColorDataLists() {
    tensor = ResourceData.of("/colorlist/" + name().toLowerCase() + ".csv");
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
