// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Collections;
import java.util.NavigableMap;
import java.util.TreeMap;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Sign;

/** functionality and suggested base class for a discrete probability distribution
 * 
 * <p>implementing classes are required to invoke
 * {@link #inverse_cdf_build(int)}, or
 * {@link #inverse_cdf_build(Chop)} in the constructor */
public abstract class EvaluatedDiscreteDistribution extends AbstractDiscreteDistribution {
  private static final Scalar _0 = DoubleScalar.of(0);
  private static final Scalar _1 = DoubleScalar.of(1);
  // ---
  /** inverse cdf maps from probability to integers and is built during random sampling generation.
   * the value type of the map is Scalar (instead of Integer) to reuse the instances of Scalar */
  private final NavigableMap<Scalar, Scalar> inverse_cdf = new TreeMap<>();

  /** precomputes a lookup map for random variate generation via {@link #quantile(Scalar)}
   * 
   * @param upperBound greatest integer n for which 0 < p(n), i.e. upper bound is inclusive
   * @see BinomialDistribution
   * @see PoissonDistribution */
  /** safeguard when computing CDF for probabilities with machine precision */
  protected void inverse_cdf_build(final int upperBound) {
    Scalar cumprob = RealScalar.ZERO;
    for (int sample = lowerBound(); sample < upperBound; ++sample) {
      Scalar prob = p_equals(sample);
      if (Scalars.nonZero(prob)) {
        cumprob = cumprob.add(prob);
        inverse_cdf.put(cumprob, RationalScalar.of(sample, 1));
        if (Scalars.lessEquals(RealScalar.ONE, cumprob))
          return;
      }
    }
    inverse_cdf.put(RealScalar.ONE, RationalScalar.of(upperBound, 1));
  }

  /** precomputes a lookup map and determines numeric upper bound
   * 
   * @param chop
   * @see PascalDistribution */
  protected void inverse_cdf_build(Chop chop) {
    int upperBound = lowerBound();
    Scalar cumprob = _0;
    while (true) {
      Scalar prob = p_equals(upperBound);
      if (Scalars.nonZero(prob)) {
        cumprob = cumprob.add(prob);
        inverse_cdf.put(cumprob, RationalScalar.of(upperBound, 1));
        if (chop.close(_1, cumprob))
          break;
      }
      ++upperBound;
    }
  }

  @Override // from InverseCDF
  public final Scalar quantile(Scalar p) {
    return Scalars.lessThan(Sign.requirePositiveOrZero(p), RealScalar.ONE) //
        ? inverse_cdf.higherEntry(p).getValue() // strictly higher
        : inverse_cdf.ceilingEntry(p).getValue();
  }

  @Override // from AbstractDiscreteDistribution
  protected final Scalar protected_quantile(Scalar p) {
    return inverse_cdf.higherEntry(p).getValue();
  }

  /** function for testing
   * 
   * @return */
  /* package */ final NavigableMap<Scalar, Scalar> inverse_cdf() {
    return Collections.unmodifiableNavigableMap(inverse_cdf);
  }
}
