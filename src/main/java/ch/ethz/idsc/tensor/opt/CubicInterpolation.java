// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Tensor;

/** interpolation order 3 */
public enum CubicInterpolation {
  ;
  /** @param control points with at least one element
   * @return function that is defined for scalars in the interval [0, control.length() - 1] */
  public static Interpolation of(Tensor control) {
    return BSplineInterpolation.of(3, control);
  }
}
