// code by jph
package ch.ethz.idsc.tensor.img;

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
  ;
  private final ColorDataIndexed colorDataIndexed = //
      new SimpleColorDataIndexed(ResourceData.of("/colorlist/" + name().substring(1) + ".csv"));

  public ColorDataIndexed getColorDataIndexed() {
    return colorDataIndexed;
  }
}
