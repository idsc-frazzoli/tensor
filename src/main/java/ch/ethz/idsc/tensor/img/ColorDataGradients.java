// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.io.ResourceData;

/** inspired by Mathematica::ColorData["Gradients"] */
public enum ColorDataGradients implements ColorDataFunction {
  CLASSIC("classic.csv"), //
  COPPER("copper.csv"), //
  GRAYSCALE("grayscale.csv"), //
  /** hsluv is hue with brightness equalized, see hsluv.org */
  HSLUV("hsluv.csv"), // <- cyclic
  HUE("hue.csv"), // <- cyclic
  PASTEL("pastel.csv"), //
  PINK("pink.csv"), //
  RAINBOW("rainbow.csv"), //
  THERMOMETER("thermometer.csv"), //
  ;
  // ---
  private final ColorDataFunction colorDataFunction;

  private ColorDataGradients(String string) {
    colorDataFunction = ColorDataGradient.of(ResourceData.of("/colorscheme/" + string));
  }

  @Override
  public Tensor apply(Scalar scalar) {
    return colorDataFunction.apply(scalar);
  }
}
