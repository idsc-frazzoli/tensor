// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.Scalar;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/BernoulliDistribution.html">BernoulliDistribution</a> */
public enum BernoulliDistribution {
  ;
  /** parameter p denotes the probability of the outcome 1
   * 
   * <p>Example:
   * <pre>
   * PDF[BernoulliDistribution[1/3], 0] == 2/3
   * PDF[BernoulliDistribution[1/3], 1] == 1/3
   * </pre>
   * 
   * @param p in the interval [0, 1]
   * @return */
  public static Distribution of(Scalar p) {
    return BinomialDistribution.of(1, p);
  }
}
