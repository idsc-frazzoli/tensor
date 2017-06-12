// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.alg.Binomial;
import ch.ethz.idsc.tensor.sca.Power;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/BinomialDistribution.html">BinomialDistribution</a> */
public class BinomialDistribution implements DiscreteDistribution {
  /** Example:
   * PDF[BinomialDistribution[10, 1/3], 1] == 5120/59049
   * 
   * @param n non-negative
   * @param p in the interval [0, 1]
   * @return */
  public static DiscreteDistribution of(int n, Scalar p) {
    if (n < 0)
      throw new RuntimeException("n=" + n);
    if (Scalars.lessThan(p, RealScalar.ZERO) || Scalars.lessThan(RealScalar.ONE, p))
      throw TensorRuntimeException.of(p);
    return new BinomialDistribution(n, p);
  }

  private final int n;
  private final Scalar p;

  private BinomialDistribution(int n, Scalar p) {
    this.n = n;
    this.p = p;
  }

  @Override // from DiscreteDistribution
  public int lowerBound() {
    return 0;
  }

  @Override // from DiscreteDistribution
  public Scalar p_equals(int k) {
    if (k < 0 || n < k)
      return RealScalar.ZERO;
    return Binomial.of(n, k).multiply(Power.of(p, k)).multiply(Power.of(RealScalar.ONE.subtract(p), n - k));
  }

  @Override // from DiscreteDistribution
  public Scalar mean() {
    return RealScalar.of(n).multiply(p);
  }
}
