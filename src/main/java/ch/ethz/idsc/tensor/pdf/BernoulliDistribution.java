// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.Scalar;

/** consistent with Mathematica::BernoulliDistribution
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/BernoulliDistribution.html">BernoulliDistribution</a> */
public enum BernoulliDistribution {
  ;
  // ---
  /** Example:
   * PDF[BernoulliDistribution[1/3], 0] == 2/3
   * 
   * @param p
   * @return */
  public static DiscreteDistribution of(Scalar p) {
    return new BinomialDistribution(1, p);
  }
}
