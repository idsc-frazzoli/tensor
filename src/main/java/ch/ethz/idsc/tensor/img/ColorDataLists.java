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
  /** mathematica default */
  _97, //
  ;
  private final Tensor tensor;
  private final List<Color> list;

  private ColorDataLists() {
    String string = "/colorlist/" + name().toLowerCase() + ".csv";
    tensor = ResourceData.of(string);
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
}
