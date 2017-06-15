// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.io.Serializable;
import java.util.Random;

import ch.ethz.idsc.tensor.Scalar;

/** capability to produce random variate
 * extends {@link Distribution} */
public interface RandomVariateInterface extends Serializable {
  /** @param random
   * @return sample generated using the given random generator */
  Scalar randomVariate(Random random);
}
