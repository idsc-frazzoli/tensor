// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** clip to an interval of non-zero width */
/* package */ class ClipInterval implements Clip {
  static final Clip UNIT = Clip.function(0, 1);
  static final Clip ABSOLUTE_ONE = Clip.function(-1, 1);
  // ---
  private final Scalar min;
  private final Scalar max;
  private final Scalar width;

  ClipInterval(Scalar min, Scalar max, Scalar width) {
    this.min = min;
    this.max = max;
    this.width = width;
  }

  @Override
  public final Scalar apply(Scalar scalar) {
    boolean cmpMin = Scalars.lessThan(scalar, min);
    boolean cmpMax = Scalars.lessThan(max, scalar);
    if (cmpMin)
      return min;
    if (cmpMax)
      return max;
    return scalar;
  }

  @Override
  @SuppressWarnings("unchecked")
  public final <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(this);
  }

  @Override
  public final boolean isInside(Scalar scalar) {
    return apply(scalar).equals(scalar);
  }

  @Override
  public final boolean isOutside(Scalar scalar) {
    return !isInside(scalar);
  }

  @Override
  public final Scalar requireInside(Scalar scalar) {
    if (isOutside(scalar))
      throw TensorRuntimeException.of(min, max, scalar);
    return scalar;
  }

  @Override
  public Scalar rescale(Scalar scalar) {
    return apply(scalar).subtract(min).divide(width);
  }

  @Override
  public final Scalar min() {
    return min;
  }

  @Override
  public final Scalar max() {
    return max;
  }

  @Override
  public final Scalar width() {
    return width;
  }
}
