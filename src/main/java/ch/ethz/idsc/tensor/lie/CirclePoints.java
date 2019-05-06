// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.RealScalar;
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
 * <p>aspects consistent with Mathematica
 * CirclePoints[0] == {}
 * CirclePoints[-1] throws an Exception
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/CirclePoints.html">CirclePoints</a> */
public enum CirclePoints {
  ;
  /** the first coordinate is always {1, 0}.
   * the orientation of the points is counter-clockwise.
   * 
   * if n == 0 the return value is the empty tensor {}
   * 
   * @param n non-negative integer
   * @return n x 2 matrix with evenly spaced points on the unit-circle
   * @throws Exception if n is negative */
  public static Tensor of(int n) {
    if (n < 0)
      throw new RuntimeException("n=" + n);
    return Range.of(0, n).divide(RealScalar.of(n)).map(AngleVector::turns);
  }
}
