// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;

/** Mathematica::ArcTan[x, y] */
public interface ArcTanInterface {
  /** @param x
   * @return ArcTan[x, this] */
  Scalar arcTan(Scalar x);
}
