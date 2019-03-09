// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** factory for the creation of {@link Clip} */
public enum Clips {
  ;
  /** @param min
   * @param max
   * @return function that clips the input to the closed interval [min, max]
   * @throws Exception if min is greater than max */
  public static Clip interval(Scalar min, Scalar max) {
    Scalar width = max.subtract(min);
    if (Sign.isNegative(width))
      throw TensorRuntimeException.of(min, max);
    return min.equals(max) //
        ? new ClipPoint(min, width)
        : new ClipInterval(min, max, width);
  }

  /** @param min
   * @param max
   * @return function that clips the input to the closed interval [min, max]
   * @throws Exception if min is greater than max */
  public static Clip interval(Number min, Number max) {
    return interval(RealScalar.of(min), RealScalar.of(max));
  }

  /***************************************************/
  private static final Clip UNIT = Clips.interval(0, 1);
  private static final Clip ABSOLUTE_ONE = Clips.interval(-1, 1);

  /** @return function that clips a scalar to the unit interval [0, 1] */
  public static Clip unit() {
    return UNIT;
  }

  /** @return function that clips a scalar to the interval [-1, 1] */
  public static Clip absoluteOne() {
    return ABSOLUTE_ONE;
  }

  /***************************************************/
  private static final Clip UNIT_NUMERIC = Clips.interval(0.0, 1.0);

  /** @return function that clips a scalar to the unit interval [0.0, 1.0] */
  public static Clip unitNumeric() {
    return UNIT_NUMERIC;
  }
}
