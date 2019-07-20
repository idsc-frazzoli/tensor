// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.Objects;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** clip to an interval of non-zero width */
/* package */ class ClipInterval implements Clip {
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
    if (Scalars.lessThan(scalar, min))
      return min;
    if (Scalars.lessThan(max, scalar))
      return max;
    return scalar;
  }

  @Override
  @SuppressWarnings("unchecked")
  public final <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(this);
  }

  @Override // from Clip
  public final boolean isInside(Scalar scalar) {
    return apply(scalar).equals(scalar);
  }

  @Override // from Clip
  public final boolean isOutside(Scalar scalar) {
    return !isInside(scalar);
  }

  @Override // from Clip
  public final Scalar requireInside(Scalar scalar) {
    if (isInside(scalar))
      return scalar;
    throw TensorRuntimeException.of(min, max, scalar);
  }

  @Override // from Clip
  public Scalar rescale(Scalar scalar) {
    return apply(scalar).subtract(min).divide(width);
  }

  @Override // from Clip
  public final Scalar min() {
    return min;
  }

  @Override // from Clip
  public final Scalar max() {
    return max;
  }

  @Override // from Clip
  public final Scalar width() {
    return width;
  }

  /***************************************************/
  @Override // from Object
  public final int hashCode() {
    return Objects.hash(min, max);
  }

  @Override // from Object
  public final boolean equals(Object object) {
    if (object instanceof Clip) {
      Clip clip = (Clip) object;
      return min.equals(clip.min()) //
          && max.equals(clip.max());
    }
    return false;
  }

  @Override // from Object
  public final String toString() {
    return "{\"min\": " + min() + ", \"max\": " + max() + "}";
  }
}
