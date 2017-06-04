// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;

/** Mathematica::ArcTan[x, y] */
public interface ArcTanInterface {
  /** @param y
   * @return ArcTan[this, y] */
  Scalar arcTan(Scalar y);
}
