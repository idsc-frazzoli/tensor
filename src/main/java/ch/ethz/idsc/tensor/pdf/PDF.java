// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.Scalar;

/** probability density function
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/PDF.html">PDF</a> */
public interface PDF {
  /** @param discreteDistribution
   * @return probability density function */
  public static PDF of(DiscreteDistribution discreteDistribution) {
    return new DefaultDiscretePDF(discreteDistribution);
  }

  /** @param discreteDistribution
   * @return probability density function */
  public static PDF of(ContinuousDistribution continuousDistribution) {
    return new DefaultContinuousPDF(continuousDistribution);
  }

  /** @param x
   * @return P(X == x), i.e. probability of random variable X == x */
  Scalar p_equals(Scalar x);

  /** @param x
   * @return P(X < x), i.e. probability of random variable X < x */
  Scalar p_lessThan(Scalar x);

  /** @param x
   * @return P(X <= x), i.e. probability of random variable X <= x */
  Scalar p_lessEquals(Scalar x);

  /** @return random sample */
  Scalar nextSample();

  /** @param random
   * @return sample generated using the given random generator */
  Scalar nextSample(Random random);

  /** @return mean of distribution */
  Scalar mean();
}
