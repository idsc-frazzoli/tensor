// code by jph
package ch.ethz.idsc.tensor.img;

import java.util.Objects;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.io.ResourceData;

/** the functions provided in the list below can be used in {@link ArrayPlot}.
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
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ColorData.html">ColorData</a>["Gradients"]
 * 
 * @see ColorDataLists */
public enum ColorDataGradients implements ColorDataGradient {
  /** classic is default
   * black, blue, cyan green, yellow, red, white */
  CLASSIC,
  /** hue is backed by {@link Hue#of(double, double, double, double)}
   * red, yellow, green, cyan, blue, violet
   * cyclic */
  HUE(HueColorData.DEFAULT),
  /** hsluv is hue with brightness equalized, see hsluv.org
   * cyclic */
  HSLUV,
  /** black, violet, red, orange, yellow, white */
  SUNSET,
  /** [0, 1] corresponds to wavelengths [380, 750] */
  VISIBLESPECTRUM,
  /** blue, cyan, yellow, red
   * inspired by Matlab::jet */
  JET,
  /** violet, blue, orange, red */
  RAINBOW,
  /** black, gray, yellow, magenta, cyan */
  CMYK_REVERSED,
  /** blue to red, has yellow before turning red */
  TEMPERATURE,
  /** blue to red, has yellow before turning red */
  TEMPERATURE_LIGHT,
  /** blue to red, symmetric */
  THERMOMETER,
  /** red-brown, white, cyan */
  BROWNCYAN,
  /** pink, yellow, cyan */
  PASTEL,
  /** orange, yellow, white */
  BEACH,
  /** green to red pastel, symmetric */
  MINT,
  /** matlab default */
  PARULA,
  /** mathematica default */
  DENSITY,
  /** red, orange, yellow */
  SOLAR,
  /** black, gray, white */
  GRAYSCALE(GrayscaleColorData.DEFAULT),
  /** black, blue-gray, white */
  BONE,
  /** black, brown, orange */
  COPPER,
  /** black, green, yellow */
  AVOCADO,
  /** the tensor library is made in Switzerland
   * the alpine color scheme was added August 1st
   * gray-blue, gray-green, light-gray, white */
  ALPINE,
  /** black, pink, white */
  PINK,
  /** black, green, yellow, brown, white */
  GREENBROWNTERRAIN,
  /** black, blue, yellow */
  STARRYNIGHT,
  /** gray, brown, orange, yellow */
  FALL,
  /** green, brown, red */
  ROSE,
  /** yellow, orange, red, magenta */
  NEON,
  /** dark-gray, dark-yellow, magenta */
  AURORA, //
  ;
  // ---
  private final ColorDataGradient colorDataGradient;

  private ColorDataGradients(ColorDataGradient colorDataGradient) {
    this.colorDataGradient = colorDataGradient;
  }

  private ColorDataGradients() {
    Tensor tensor = ResourceData.of("/colorscheme/" + name().toLowerCase() + ".csv");
    boolean success = Objects.nonNull(tensor);
    colorDataGradient = success //
        ? new LinearColorDataGradient(tensor)
        : FallbackColorDataGradient.INSTANCE;
    if (!success)
      System.err.println("fail to load " + name());
  }

  @Override // from ColorDataGradient
  public Tensor apply(Scalar scalar) {
    return colorDataGradient.apply(scalar);
  }

  @Override // from ColorDataGradient
  public ColorDataGradient deriveWithOpacity(Scalar opacity) {
    return colorDataGradient.deriveWithOpacity(opacity);
  }
}
