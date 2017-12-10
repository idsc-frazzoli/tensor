// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.alg.Binomial;

/** Quote from Mathematica:
 * "A hypergeometric distribution gives the distribution of the number of successes
 * in N draws from a population of size m_n containing n successes."
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/HypergeometricDistribution.html">HypergeometricDistribution</a> */
public class HypergeometricDistribution extends EvaluatedDiscreteDistribution implements VarianceInterface {
  /** see the Mathematica documentation of HypergeometricDistribution
   * 
   * @param N number of draws
   * @param n number of successes
   * @param m_n population size
   * @return */
  public static Distribution of(int N, int n, int m_n) {
    // (0 < N && N <= m_n && 0 <= n && n <= m_n)
    if (N <= 0 || m_n < N || n < 0 || m_n < n)
      throw new RuntimeException(String.format("N=%d n=%d m_n=%d", N, n, m_n));
    return new HypergeometricDistribution(N, n, m_n);
  }

  // ---
  private final int N;
  private final int n;
  private final int m_n;
  private final int m;
  private final Binomial binomial_n;
  private final Binomial binomial_m;
  private final Binomial binomial_m_n;

  private HypergeometricDistribution(int N, int n, int m_n) {
    this.N = N;
    this.n = n;
    this.m_n = m_n;
    this.m = m_n - n;
    binomial_n = Binomial.of(n);
    binomial_m = Binomial.of(m);
    binomial_m_n = Binomial.of(m_n);
    inverse_cdf_build();
  }

  @Override // from MeanInterface
  public Scalar mean() {
    return RationalScalar.of(N, 1).multiply(RationalScalar.of(n, m_n));
  }

  @Override // from VarianceInterface
  public Scalar variance() {
    // ((mpn - n) n (mpn - N) N) / ((-1 + mpn) mpn^2)
    Scalar rd1 = RationalScalar.of(m_n - n, m_n);
    Scalar rd2 = RationalScalar.of(m_n - N, m_n);
    // ( n N) / (-1 + mpn)
    Scalar rd3 = RationalScalar.of(N, m_n - 1);
    // ( n )
    Scalar rd4 = RationalScalar.of(n, 1);
    return rd1.multiply(rd2).multiply(rd3).multiply(rd4);
  }

  @Override // from DiscreteDistribution
  public int lowerBound() {
    return 0;
  }

  @Override // from EvaluatedDiscreteDistribution
  protected int upperBound() {
    return Math.min(N, n);
  }

  @Override // from AbstractDiscreteDistribution
  protected Scalar protected_p_equals(int i) {
    if (N < i || n < i)
      return RealScalar.ZERO;
    return binomial_n.over(i).multiply(binomial_m.over(N - i)).divide(binomial_m_n.over(N));
  }

  @Override // from Object
  public String toString() {
    return String.format("%s[%d, %d, %d]", getClass().getSimpleName(), N, n, m_n);
  }
}
