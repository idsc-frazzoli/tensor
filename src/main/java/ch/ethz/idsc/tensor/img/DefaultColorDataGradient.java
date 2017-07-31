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

public class DefaultColorDataGradient implements ColorDataFunction {
  private static final Tensor TRANSPARENT = Array.zeros(4).unmodifiable();

  public static ColorDataFunction of(Tensor tensor) {
    return new DefaultColorDataGradient(tensor);
  }

  // ---
  private final Tensor tensor;
  private final Interpolation interpolation;
  private final Scalar scale;

  private DefaultColorDataGradient(Tensor tensor) {
    this.tensor = N.of(tensor);
    interpolation = LinearInterpolation.of(this.tensor);
    scale = DoubleScalar.of(tensor.length() - 1);
  }

  @Override
  public Tensor apply(Scalar scalar) {
    return NumberQ.of(scalar) ? //
        interpolation.get(Tensors.of(scalar.multiply(scale))) : TRANSPARENT;
  }

  public Tensor table() {
    return tensor.unmodifiable();
  }
}
