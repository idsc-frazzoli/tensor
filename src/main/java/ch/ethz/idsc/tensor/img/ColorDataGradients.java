// code by jph
package ch.ethz.idsc.tensor.img;

import java.util.Objects;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.io.ResourceData;
import ch.ethz.idsc.tensor.opt.ScalarTensorFunction;

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
 * <p>inspired by Mathematica::ColorData["Gradients"] */
public enum ColorDataGradients implements ScalarTensorFunction {
  /** classic is default */
  CLASSIC, //
  /** hue is backed by {@link Hue#of(double, double, double, double)} */
  HUE(HueColorData.FUNCTION), // <- cyclic
  /** hsluv is hue with brightness equalized, see hsluv.org */
  HSLUV, // <- cyclic
  SUNSET, //
  /** [0, 1] corresponds to wavelengths [380, 750] */
  VISIBLESPECTRUM, //
  JET, //
  RAINBOW, //
  CMYK_REVERSED, //
  /** blue to red, has yellow before turning red */
  TEMPERATURE, //
  /** blue to red, has yellow before turning red */
  TEMPERATURE_LIGHT, //
  /** blue to red, symmetric */
  THERMOMETER, //
  BROWNCYAN, //
  PASTEL, //
  BEACH, //
  /** green to red pastel, symmetric */
  MINT, //
  /** matlab default */
  PARULA, //
  /** mathematica default */
  DENSITY, //
  SOLAR, //
  GRAYSCALE(GrayscaleColorData.FUNCTION), //
  BONE, //
  COPPER, //
  AVOCADO, //
  /** the tensor library is made in Switzerland
   * the alpine color scheme was added August 1st */
  ALPINE, //
  PINK, //
  GREENBROWNTERRAIN, //
  STARRYNIGHT, //
  FALL, //
  ROSE, //
  NEON, //
  AURORA, //
  ;
  // ---
  private final ScalarTensorFunction scalarTensorFunction;

  private ColorDataGradients(ScalarTensorFunction scalarTensorFunction) {
    this.scalarTensorFunction = scalarTensorFunction;
  }

  private ColorDataGradients() {
    Tensor tensor = ResourceData.of("/colorscheme/" + name().toLowerCase() + ".csv");
    boolean success = Objects.nonNull(tensor);
    scalarTensorFunction = success ? ColorDataGradient.of(tensor) : StaticHelper.FALLBACK;
    if (!success)
      System.err.println("fail to load " + name());
  }

  @Override
  public Tensor apply(Scalar scalar) {
    return scalarTensorFunction.apply(scalar);
  }
}
