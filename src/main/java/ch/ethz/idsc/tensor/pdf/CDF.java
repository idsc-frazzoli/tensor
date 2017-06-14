// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.Scalar;

/** cumulative distribution function
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/CDF.html">CDF</a> */
public interface CDF extends PDF {
  /** @param distribution
   * @return cumulative distribution function */
  public static CDF of(Distribution distribution) {
    if (distribution instanceof DiscreteDistribution)
      return new DiscreteCDF((DiscreteDistribution) distribution);
    if (distribution instanceof ContinuousDistribution)
      return new ContinuousCDF((ContinuousDistribution) distribution);
    throw new RuntimeException();
  }

  /** @param x
   * @return P(X < x), i.e. probability of random variable X < x */
  Scalar p_lessThan(Scalar x);

  /** @param x
   * @return P(X <= x), i.e. probability of random variable X <= x */
  Scalar p_lessEquals(Scalar x);
}
