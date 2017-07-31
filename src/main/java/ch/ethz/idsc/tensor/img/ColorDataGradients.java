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
  HUE("hue.csv"), // <- periodic
  PASTEL("pastel.csv"), //
  PINK("pink.csv"), //
  RAINBOW("rainbow.csv"), //
  THERMOMETER("thermometer.csv"), //
  ;
  // ---
  private final ColorDataFunction colorDataFunction;

  private ColorDataGradients(String string) {
    Tensor tensor = ResourceData.of("/colorscheme/" + string);
    colorDataFunction = DefaultColorDataGradient.of(tensor);
  }

  @Override
  public Tensor apply(Scalar scalar) {
    return colorDataFunction.apply(scalar);
  }
}
