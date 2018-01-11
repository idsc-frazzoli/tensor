// code by jph
package ch.ethz.idsc.tensor.opt;

import java.io.Serializable;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.ScalarQ;
import ch.ethz.idsc.tensor.Tensor;

/** suggested base class for implementations of {@link Interpolation} */
public abstract class AbstractInterpolation implements Interpolation, Serializable {
  @Override // from Interpolation
  public final Tensor get(Tensor index) {
    ScalarQ.thenThrow(index);
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
