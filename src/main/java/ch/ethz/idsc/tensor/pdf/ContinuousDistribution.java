// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.Scalar;

/** functionality for a continuous probability distribution */
public interface ContinuousDistribution extends Distribution {
  /** @param x
   * @return P(X < x), i.e. probability of random variable X < x */
  Scalar p_lessThan(Scalar x);

  /** @param x
   * @return P(X <= x), i.e. probability of random variable X <= x */
  Scalar p_lessEquals(Scalar x);

  /** @param random
   * @return sample derived from given random generator */
  Scalar nextSample(Random random);
}
