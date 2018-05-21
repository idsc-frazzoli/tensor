// code by jph
package ch.ethz.idsc.tensor.opt;

import java.io.Serializable;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** suggested base class for implementations of {@link Interpolation} */
public abstract class AbstractInterpolation implements Interpolation, Serializable {
  @Override // from Interpolation
  public final Scalar Get(Tensor index) {
    return (Scalar) get(index);
  }

  @Override // from Interpolation
  public final Scalar At(Scalar index) {
    return (Scalar) at(index);
  }
}
