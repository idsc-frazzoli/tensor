// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.Scalar;

/** capability to produce random variate */
public interface RandomVariateInterface {
  /** @param random
   * @return sample generated using the given random generator */
  Scalar randomVariate(Random random);
}
