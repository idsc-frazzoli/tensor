// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.Scalar;

/** inverse of the cumulative distribution function of a given distribution
 * 
 * <p>All {@link DiscreteDistribution}s implement the interface {@link InverseCDF}.
 * 
 * <p>Several continuous distributions support the functionality, for instance
 * {@link UniformDistribution}, {@link ExponentialDistribution}, and
 * {@link FrechetDistribution}.
 * 
 * <p>One application of InverseCDF(distribution) is to generate {@link RandomVariate}s
 * respective of the given distribution from uniform random numbers in the unit interval.
 * 
 * <p>Another application is to state confidence intervals to test hypotheses.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/InverseCDF.html">InverseCDF</a> */
public interface InverseCDF {
  /** @param distribution
   * @return inverse of the cumulative distribution function
   * @throws Exception if distribution does not implement the inverse CDF */
  static InverseCDF of(Distribution distribution) {
    return (InverseCDF) distribution;
  }

  /** the inverse CDF at p is also referred to as the p-th quantile of a distribution
   * 
   * @param p in half-open interval [0, 1). The case p == 1 is documented in the implementing class.
   * @return x largest for which P(X <= x) <= p
   * @throws Exception if given p is outside of the interval [0, 1] */
  Scalar quantile(Scalar p);
}
