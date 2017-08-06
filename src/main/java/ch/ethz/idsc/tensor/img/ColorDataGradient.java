// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.NumberQ;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.opt.Interpolation;
import ch.ethz.idsc.tensor.opt.LinearInterpolation;
import ch.ethz.idsc.tensor.sca.N;

/** ColorDataGradient maps a {@link Scalar} to a color using interpolation
 * on a given table of rgba values.
 * 
 * <p>In case NumberQ.of(scalar) == false then a transparent color is assigned. */
public class ColorDataGradient implements ColorDataFunction {
  private static final Tensor TRANSPARENT = Array.zeros(4).unmodifiable();

  /** colors are generated using {@link LinearInterpolation} of given tensor
   * 
   * @param tensor n x 4 where each row contains {r,g,b,a} with values ranging in [0, 255]
   * @return */
  public static ColorDataGradient of(Tensor tensor) {
    return new ColorDataGradient(tensor);
  }

  // ---
  private final Tensor tensor;
  private final Interpolation interpolation;
  private final Scalar scale;

  private ColorDataGradient(Tensor tensor) {
    this.tensor = N.of(tensor);
    interpolation = LinearInterpolation.of(this.tensor);
    scale = DoubleScalar.of(tensor.length() - 1);
  }

  @Override
  public Tensor apply(Scalar scalar) {
    return NumberQ.of(scalar) ? //
        interpolation.get(Tensors.of(scalar.multiply(scale))) : TRANSPARENT;
  }

  /** the application of this function is to derive a new color scheme
   * from an existing one, for instance to modify the brightness or transparency
   * 
   * @return n x 4 table with rows as {r,g,b,a} values */
  /* package for testing */ Tensor rgbaTable() {
    return tensor.unmodifiable();
  }
}
