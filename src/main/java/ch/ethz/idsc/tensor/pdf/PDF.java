// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.Scalar;

/** probability density function
 * 
 * inspired by
 * <a href="https://reference.wolfram.com/language/ref/PDF.html">PDF</a> */
public interface PDF {
  /** @param discreteDistribution
   * @return */
  public static PDF of(DiscreteDistribution discreteDistribution) {
    return new DefaultDiscretePDF(discreteDistribution);
  }

  /** @param x
   * @return P(X == x), i.e. probability of random variable X == x */
  Scalar p_equals(Scalar x);

  /** @param x
   * @return */
  Scalar p_lessThan(Scalar x);

  /** @param x
   * @return */
  Scalar p_lessEquals(Scalar x);

  /** @return */
  Scalar nextSample();

  /** @param random
   * @return */
  Scalar nextSample(Random random);
}
