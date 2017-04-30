// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.io.ExtractPrimitives;
import ch.ethz.idsc.tensor.sca.Round;

/** entries of index are rounded to nearest integers */
public class NearestInterpolation implements Interpolation {
  /** @param tensor
   * @return */
  public static Interpolation of(Tensor tensor) {
    return new NearestInterpolation(tensor);
  }

  final Tensor tensor;

  NearestInterpolation(Tensor tensor) {
    this.tensor = tensor;
  }

  @Override
  public Tensor get(Tensor index) {
    if (index.isScalar())
      throw TensorRuntimeException.of(index);
    return tensor.get(ExtractPrimitives.toListInteger(Round.of(index)));
  }
}
