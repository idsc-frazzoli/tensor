// code by jph
package ch.ethz.idsc.tensor.img;

import java.util.Objects;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.io.ResourceData;

/** the {@link ColorDataFunction}s provided in the list below can be used in {@link ArrayPlot}.
 * 
 * <p>To obtain a single color value use
 * <pre>
 * Color color = ColorFormat.toColor(ColorDataGradients.THERMOMETER.apply(RealScalar.of(0.78)));
 * </pre>
 * 
 * <p>The {@link ColorDataGradients#HUE} and {@link ColorDataGradients#GRAYSCALE} are
 * hard-coded, which may give a performance advantage.
 * The remaining color data gradients are backed by linear interpolation over a predefined
 * table of RGBA values.
 * 
 * <p>inspired by Mathematica::ColorData["Gradients"] */
public enum ColorDataGradients implements ColorDataFunction {
  /** classic is default */
  CLASSIC("classic.csv"), //
  /** hue is backed by {@link Hue#of(double, double, double, double)} */
  HUE(HueColorData.FUNCTION), // <- cyclic
  /** hsluv is hue with brightness equalized, see hsluv.org */
  HSLUV("hsluv.csv"), // <- cyclic
  SUNSET("sunset.csv"), //
  RAINBOW("rainbow.csv"), //
  CMYK_REVERSED("cmyk_reversed.csv"), //
  THERMOMETER("thermometer.csv"), //
  PASTEL("pastel.csv"), //
  GRAYSCALE(GrayscaleColorData.FUNCTION), //
  /** the tensor library is made in Switzerland
   * the alpine color scheme was added August 1st */
  ALPINE("alpine.csv"), //
  COPPER("copper.csv"), //
  PINK("pink.csv"), //
  ;
  // ---
  private final ColorDataFunction colorDataFunction;

  private ColorDataGradients(ColorDataFunction colorDataFunction) {
    this.colorDataFunction = colorDataFunction;
  }

  private ColorDataGradients(String string) {
    Tensor tensor = ResourceData.of("/colorscheme/" + string);
    boolean failure = Objects.isNull(tensor);
    colorDataFunction = ColorDataGradient.of(failure ? Array.zeros(2, 4) : tensor);
    if (failure)
      System.err.println("fail to load " + string);
  }

  @Override
  public Tensor apply(Scalar scalar) {
    return colorDataFunction.apply(scalar);
  }
}
