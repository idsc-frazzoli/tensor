// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Objects;

import ch.ethz.idsc.tensor.Scalar;

/** cumulative distribution function
 * 
 * CDF extends the capabilities of {@link PDF}
 * 
 * {@link DiscreteDistribution}s may extend CDF if the implementation
 * is beneficial for computational efficiency and numerical robustness.
 * Examples: {@link DiscreteUniformDistribution}, and {@link GeometricDistribution}.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/CDF.html">CDF</a> */
public interface CDF {
  /** @param distribution
   * @return cumulative distribution function
   * @throws Exception if distribution does not support the computation of the CDF */
  static CDF of(Distribution distribution) {
    if (distribution instanceof CDF)
      return (CDF) distribution;
    if (distribution instanceof DiscreteDistribution)
      return new DiscreteCDF((DiscreteDistribution) distribution);
    Objects.requireNonNull(distribution);
    throw new RuntimeException(distribution.getClass().getName());
  }

  /** @param x
   * @return P(X < x), i.e. probability of random variable X < x */
  Scalar p_lessThan(Scalar x);

  /** @param x
   * @return P(X <= x), i.e. probability of random variable X <= x */
  Scalar p_lessEquals(Scalar x);
}
