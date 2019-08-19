// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;

/** when the computation cannot be carried out, the implementation should return
 * <pre>
 * ArcTan.FUNCTION.apply(divide(x))
 * </pre>
 * 
 * Mathematica::ArcTan[x, y]
 * 
 * @see ArcTan */
@FunctionalInterface
public interface ArcTanInterface {
  /** @param x
   * @return ArcTan[x, this] == ArcTan[ this / x ] */
  Scalar arcTan(Scalar x);
}
