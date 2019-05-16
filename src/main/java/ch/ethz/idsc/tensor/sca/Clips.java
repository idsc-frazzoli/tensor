// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** factory for the creation of {@link Clip} */
public enum Clips {
  ;
  /** clips in the interval [min, ..., max]
   * 
   * @param min
   * @param max
   * @return function that clips the input to the closed interval [min, max]
   * @throws Exception if min is greater than max */
  public static Clip interval(Scalar min, Scalar max) {
    Scalar width = max.subtract(min);
    SignInterface signInterface = (SignInterface) width;
    switch (signInterface.signInt()) {
    case 0:
      return new ClipPoint(min, width);
    case 1:
      return new ClipInterval(min, max, width);
    }
    throw TensorRuntimeException.of(min, max);
  }

  /** @param min
   * @param max
   * @return function that clips the input to the closed interval [min, max]
   * @throws Exception if min is greater than max */
  public static Clip interval(Number min, Number max) {
    return interval(RealScalar.of(min), RealScalar.of(max));
  }

  /***************************************************/
  /** clips in the interval [0, ..., max]
   * 
   * @param max non-negative
   * @return function that clips the input to the closed interval [0, max]
   * @throws Exception if max is negative */
  public static Clip positive(Scalar max) {
    return interval(max.zero(), max);
  }

  /** @param max non-negative
   * @return function that clips the input to the closed interval [0, max]
   * @throws Exception if max is negative */
  public static Clip positive(Number max) {
    return positive(RealScalar.of(max));
  }

  /***************************************************/
  /** clips in the interval [-max, ..., max]
   * 
   * @param max non-negative
   * @return function that clips the input to the closed interval [-max, max]
   * @throws Exception if max is negative */
  public static Clip absolute(Scalar max) {
    return interval(max.negate(), max);
  }

  /** @param max non-negative
   * @return function that clips the input to the closed interval [-max, max]
   * @throws Exception if max is negative */
  public static Clip absolute(Number max) {
    return absolute(RealScalar.of(max));
  }

  /***************************************************/
  private static final Clip UNIT = positive(1);
  private static final Clip ABSOLUTE_ONE = absolute(1);

  /** @return function that clips a scalar to the unit interval [0, 1] */
  public static Clip unit() {
    return UNIT;
  }

  /** @return function that clips a scalar to the interval [-1, 1] */
  public static Clip absoluteOne() {
    return ABSOLUTE_ONE;
  }
}
