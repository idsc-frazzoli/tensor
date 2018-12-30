// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Tensor;

public enum CubicInterpolation {
  ;
  /** @param control points with at least one element
   * @return */
  public static Interpolation of(Tensor control) {
    return BSplineInterpolation.of(3, control);
  }
}
