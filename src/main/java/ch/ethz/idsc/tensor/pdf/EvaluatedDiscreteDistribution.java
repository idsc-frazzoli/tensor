// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Collections;
import java.util.NavigableMap;
import java.util.TreeMap;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.sca.Sign;

/** functionality and suggested base class for a discrete probability distribution
 * 
 * <p>implementing classes are required to invoke {@link #inverse_cdf_build()}
 * in the constructor */
public abstract class EvaluatedDiscreteDistribution extends AbstractDiscreteDistribution {
  /** inverse cdf maps from probability to integers and is built during random sampling generation.
   * the value type of the map is Scalar (instead of Integer) to reuse the instances of Scalar */
  private final NavigableMap<Scalar, Scalar> inverse_cdf = new TreeMap<>();

  /** precomputes a lookup map for random variate generation via {@link #quantile(Scalar)} */
  protected void inverse_cdf_build() {
    Scalar cumprob = RealScalar.ZERO;
    for (int sample = lowerBound(); sample < upperBound(); ++sample) {
      Scalar prob = p_equals(sample);
      if (Scalars.nonZero(prob)) {
        cumprob = cumprob.add(prob);
        inverse_cdf.put(cumprob, RationalScalar.of(sample, 1));
        if (Scalars.lessEquals(RealScalar.ONE, cumprob))
          return;
      }
    }
    inverse_cdf.put(RealScalar.ONE, RationalScalar.of(upperBound(), 1));
  }

  @Override // from InverseCDF
  public final Scalar quantile(Scalar p) {
    if (Sign.isNegative(p))
      throw TensorRuntimeException.of(p);
    return Scalars.lessThan(p, RealScalar.ONE) //
        ? inverse_cdf.higherEntry(p).getValue() // strictly higher
        : inverse_cdf.ceilingEntry(p).getValue();
  }

  @Override // from AbstractDiscreteDistribution
  protected final Scalar protected_quantile(Scalar p) {
    return inverse_cdf.higherEntry(p).getValue();
  }

  /** safeguard when computing CDF for probabilities with machine precision
   * 
   * @return greatest integer n for which 0 < p(n), i.e. upper bound is inclusive */
  protected abstract int upperBound();

  /* package for testing */ final NavigableMap<Scalar, Scalar> inverse_cdf() {
    return Collections.unmodifiableNavigableMap(inverse_cdf);
  }
}
