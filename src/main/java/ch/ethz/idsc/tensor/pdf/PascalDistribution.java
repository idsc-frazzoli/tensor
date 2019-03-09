// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.alg.Binomial;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Clips;
import ch.ethz.idsc.tensor.sca.Power;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/PascalDistribution.html">PascalDistribution</a> */
public class PascalDistribution extends EvaluatedDiscreteDistribution implements VarianceInterface {
  /** @param n number of successes
   * @param p success probability
   * @return distribution of the number of trials with success probability p before n successes occur */
  public static Distribution of(int n, Scalar p) {
    if (n <= 0)
      throw new RuntimeException("n=" + n);
    return new PascalDistribution(n, Clips.unit().requireInside(p));
  }

  // ---
  private final int n;
  private final Scalar p;
  private final Scalar o_p;

  private PascalDistribution(int n, Scalar p) {
    this.n = n;
    this.p = p;
    o_p = RealScalar.ONE.subtract(p);
    inverse_cdf_build(Chop._14);
  }

  @Override // from DiscreteDistribution
  public int lowerBound() {
    return n;
  }

  @Override // from MeanInterface
  public Scalar mean() {
    return RealScalar.of(n).divide(p);
  }

  @Override // from VarianceInterface
  public Scalar variance() {
    return mean().multiply(o_p).divide(p);
  }

  @Override // from AbstractDiscreteDistribution
  protected Scalar protected_p_equals(int x) { // lowerBound() <= x
    return Power.of(o_p, x - n).multiply(Power.of(p, n)).multiply(Binomial.of(x - 1, n - 1));
  }
}
