// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.NumberQ;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.io.ResourceData;
import ch.ethz.idsc.tensor.opt.Interpolation;
import ch.ethz.idsc.tensor.opt.LinearInterpolation;

/** inspired by Mathematica::ColorData["Gradients"] */
// EXPERIMENTAL
public enum ColorDataGradients implements ColorDataFunction {
  CLASSIC("classic.csv"), //
  GRAYSCALE("grayscale.csv"), //
  HUE("hue.csv"), //
  PASTEL("pastel.csv"), //
  RAINBOW("rainbow.csv"), //
  THERMOMETER("thermometer.csv"), //
  ;
  private static final Tensor INDETERMINATE = Array.zeros(4).unmodifiable();
  // ---
  private final Interpolation interpolation;
  private final Scalar scale;

  private ColorDataGradients(String string) {
    Tensor tensor = ResourceData.of("/colorscheme/" + string);
    interpolation = LinearInterpolation.of(tensor);
    scale = DoubleScalar.of(tensor.length() - 1);
  }

  @Override
  public Tensor apply(Scalar scalar) {
    return NumberQ.of(scalar) ? //
        interpolation.get(Tensors.of(scalar.multiply(scale))) : INDETERMINATE;
  }
}
