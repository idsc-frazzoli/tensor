// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;

/** {@link Ceiling}, {@link Floor}, {@link Round} */
public interface RoundingInterface {
  /** @return equal or higher scalar with integer components */
  Scalar ceiling();

  /** @return equal or lower scalar with integer components */
  Scalar floor();

  /** @return closest scalar with integer components */
  Scalar round();
}
