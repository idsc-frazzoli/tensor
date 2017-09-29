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
 * hard-coded, which gives a performance advantage.
 * The remaining color data gradients are backed by linear interpolation over a predefined
 * table of RGBA values implemented in {@link ColorDataGradient}.
 * 
 * <p>inspired by Mathematica::ColorData["Gradients"] */
public enum ColorDataGradients implements ColorDataFunction {
  /** classic is default */
  CLASSIC, //
  /** hue is backed by {@link Hue#of(double, double, double, double)} */
  HUE(HueColorData.FUNCTION), // <- cyclic
  /** hsluv is hue with brightness equalized, see hsluv.org */
  HSLUV, // <- cyclic
  SUNSET, //
  RAINBOW, //
  CMYK_REVERSED, //
  TEMPERATURE, // blue to red, has yellow before turning red
  TEMPERATURE_LIGHT, // blue to red, has yellow before turning red
  THERMOMETER, // blue to red, symmetric
  MINT, // green to red pastel, symmetric
  PASTEL, //
  BEACH, //
  PARULA, // matlab default
  SOLAR, //
  GRAYSCALE(GrayscaleColorData.FUNCTION), //
  /** the tensor library is made in Switzerland
   * the alpine color scheme was added August 1st */
  ALPINE, //
  COPPER, //
  PINK, //
  ;
  // ---
  private final ColorDataFunction colorDataFunction;

  private ColorDataGradients(ColorDataFunction colorDataFunction) {
    this.colorDataFunction = colorDataFunction;
  }

  private ColorDataGradients() {
    Tensor tensor = ResourceData.of("/colorscheme/" + name().toLowerCase() + ".csv");
    boolean failure = Objects.isNull(tensor);
    colorDataFunction = ColorDataGradient.of(failure ? Array.zeros(2, 4) : tensor);
    if (failure)
      System.err.println("fail to load " + name());
  }

  @Override
  public Tensor apply(Scalar scalar) {
    return colorDataFunction.apply(scalar);
  }
}
