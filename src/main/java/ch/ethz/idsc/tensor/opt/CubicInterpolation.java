// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

class CubicInterpolation extends AbstractInterpolation {
  public static Interpolation of(Tensor tensor) {
    return new CubicInterpolation(tensor);
  }

  // ---
  @SuppressWarnings("unused")
  private final Tensor tensor;

  private CubicInterpolation(Tensor tensor) {
    if (tensor == null)
      throw new RuntimeException();
    this.tensor = tensor;
  }

  @Override // from AbstractInterpolation
  protected final Tensor _get(Tensor index) {
    // TODO implement
    throw TensorRuntimeException.of(index);
  }
}
