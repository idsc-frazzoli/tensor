// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.alg.Binomial;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/HypergeometricDistribution.html">HypergeometricDistribution</a> */
public class HypergeometricDistribution extends AbstractDiscreteDistribution implements VarianceInterface {
  /** see the Mathematica documentation of HypergeometricDistribution
   * 
   * @param N
   * @param n
   * @param m_n
   * @return */
  public static Distribution of(int N, int n, int m_n) {
    if (N <= 0 || m_n < N || m_n < n) // TODO document conditions
      throw new RuntimeException();
    return new HypergeometricDistribution(N, n, m_n);
  }

  // ---
  private final int N;
  private final int n;
  private final int m_n;
  private final int m;

  private HypergeometricDistribution(int N, int n, int m_n) {
    this.N = N;
    this.n = n;
    this.m_n = m_n;
    this.m = m_n - n;
  }

  @Override // from MeanInterface
  public Scalar mean() {
    return RealScalar.of(N).multiply(RationalScalar.of(n, m_n));
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

  @Override // from DiscreteDistribution
  public Scalar p_equals(int i) {
    if (N < i)
      return RealScalar.ZERO;
    return Binomial.of(n, i).multiply(Binomial.of(m, N - i)).divide(Binomial.of(m_n, N));
  }
}
