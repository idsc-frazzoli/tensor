// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Range;

/** implementation is only consistent with Mathematica up to rotation around coordinate (0, 0)
 * 
 * <p>For instance, the points
 * Mathematica::CirclePoints[3] == {{Sqrt[3]/2, -(1/2)}, {0, 1}, {-(Sqrt[3]/2), -(1/2)}}
 * are mapped onto
 * Tensor::CirclePoints[3] == {{1.000, 0.000}, {-0.500, 0.866}, {-0.500, -0.866}}
 * using a rotation around (0, 0) by Pi/6.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/CirclePoints.html">CirclePoints</a> */
public enum CirclePoints {
  ;
  /** the first coordinate is always {1, 0}.
   * the orientation of the points is counter-clockwise.
   * 
   * @param n
   * @return n x 2 matrix with evenly spaced points on the unit-circle */
  public static Tensor of(int n) {
    return Range.of(0, n).multiply(DoubleScalar.of(2 * Math.PI / n)).map(AngleVector::of);
  }
}
