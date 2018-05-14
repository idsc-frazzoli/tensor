// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.io.ResourceData;

/** inspired by Mathematica::ColorData[97] */
public enum ColorDataLists {
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
  /** hue palette with 13 colors normalized according to brightness, tensor library default */
  _250, // luma
  /** hue palette with 13 colors */
  _251, //
  ;
  private final Tensor tensor = ResourceData.of(StaticHelper.colorlist(name()));
  private final ColorDataIndexed colorDataIndexed = new CyclicColorDataIndexed(tensor);

  /** @return */
  public ColorDataIndexed cyclic() {
    return colorDataIndexed;
  }

  /** @return number of unique colors before cycling */
  public int size() {
    return tensor.length();
  }
}
