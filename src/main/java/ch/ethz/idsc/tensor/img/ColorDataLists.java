// code by jph
package ch.ethz.idsc.tensor.img;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

import ch.ethz.idsc.tensor.NumberQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.io.ResourceData;
import ch.ethz.idsc.tensor.sca.Floor;

/** inspired by Mathematica::ColorData[97] */
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
  _103, //
  _104, //
  _106, //
  _108, //
  _109, //
  _110, //
  _112, //
  ;
  /** matrix with dimensions N x 4 */
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

  /** @param alpha
   * @return new instance of ColorDataIndexed with identical RGB color values
   * but with transparency as given alpha */
  public ColorDataIndexed deriveWithAlpha(int alpha) {
    Tensor tensor = this.tensor.copy();
    Scalar scalar = RealScalar.of(alpha);
    tensor.set(entry -> scalar, Tensor.ALL, 3);
    return new SimpleColorDataList(tensor);
  }
}
