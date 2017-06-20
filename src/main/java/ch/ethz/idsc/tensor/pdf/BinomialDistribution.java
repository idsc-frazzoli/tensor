// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.alg.Binomial;
import ch.ethz.idsc.tensor.sca.Power;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/BinomialDistribution.html">BinomialDistribution</a> */
public class BinomialDistribution extends AbstractDiscreteDistribution implements VarianceInterface {
  /** Example:
   * PDF[BinomialDistribution[10, 1/3], 1] == 5120/59049
   * 
   * @param n non-negative
   * @param p in the interval [0, 1]
   * @return */
  public static Distribution of(int n, Scalar p) {
    if (n < 0)
      throw new RuntimeException("n=" + n);
    if (Scalars.lessThan(p, RealScalar.ZERO) || Scalars.lessThan(RealScalar.ONE, p))
      throw TensorRuntimeException.of(p);
    return new BinomialDistribution(n, p);
  }

  /** @param n non-negative integer
   * @param p in the interval [0, 1]
   * @return */
  public static Distribution of(Scalar n, Scalar p) {
    return of(Scalars.intValueExact(n), p);
  }

  // ---
  private final int n;
  private final Scalar p;
  private final Binomial binomial;

  private BinomialDistribution(int n, Scalar p) {
    this.n = n;
    this.p = p;
    binomial = Binomial.of(n);
  }

  @Override // from MeanInterface
  public Scalar mean() {
    return RealScalar.of(n).multiply(p);
  }

  @Override // from VarianceInterface
  public Scalar variance() {
    return RealScalar.of(n).multiply(p).multiply(RealScalar.ONE.subtract(p));
  }

  @Override // from DiscreteDistribution
  public int lowerBound() {
    return 0;
  }

  @Override // from AbstractDiscreteDistribution
  protected Scalar protected_p_equals(int k) {
    if (n < k)
      return RealScalar.ZERO;
    return binomial.over(k).multiply(Power.of(p, k)).multiply(Power.of(RealScalar.ONE.subtract(p), n - k));
  }

  @Override
  public Scalar randomVariate(Random random) {
    // if (n <= 20)
    // return super.randomVariate(random);
    // extension thanks to claudio ruch
    int k = 0;
    double p_double = p.number().doubleValue(); // TODO test if 1-p or p
    for (int index = 0; index < n; ++index)
      if (random.nextDouble() < p_double)
        ++k;
    return RealScalar.of(k);
  }
}
