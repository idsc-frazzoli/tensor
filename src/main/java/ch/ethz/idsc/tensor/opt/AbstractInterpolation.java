// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** suggested base class for implementations of {@link Interpolation} */
public abstract class AbstractInterpolation implements Interpolation {
  @Override // from Interpolation
  public final Tensor get(Tensor index) {
    if (index.isScalar())
      throw TensorRuntimeException.of(index);
    return _get(index);
  }

  @Override // from Interpolation
  public final Scalar Get(Tensor index) {
    return (Scalar) get(index);
  }

  /***************************************************/
  /** @param index asserted to be instance of {@link Tensor} and not {@link Scalar}
   * @return */
  protected abstract Tensor _get(Tensor index);
}
