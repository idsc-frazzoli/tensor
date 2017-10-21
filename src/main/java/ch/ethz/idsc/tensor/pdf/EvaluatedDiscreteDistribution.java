// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Collections;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.TreeMap;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** functionality and suggested base class for a discrete probability distribution */
public abstract class EvaluatedDiscreteDistribution extends AbstractDiscreteDistribution {
  /** inverse cdf maps from probability to integers and is built during random sampling generation.
   * the value type of the map is Scalar (instead of Integer) to reuse the instances of Scalar */
  private final NavigableMap<Scalar, Scalar> inverse_cdf = new TreeMap<>();

  /** @param reference in the half-open interval [0, 1)
   * @return */
  @Override // from AbstractDiscreteDistribution
  public final synchronized Scalar quantile(Scalar reference) {
    // if the input is outside the valid range, the while loop below may never terminate
    if (Scalars.lessThan(reference, RealScalar.ZERO) || Scalars.lessEquals(RealScalar.ONE, reference))
      throw TensorRuntimeException.of(reference);
    // ---
    if (inverse_cdf.isEmpty())
      inverse_cdf.put(p_equals(lowerBound()), RationalScalar.of(lowerBound(), 1));
    // ---
    Entry<Scalar, Scalar> higher = inverse_cdf.higherEntry(reference); // strictly higher
    if (Objects.isNull(higher)) {
      Entry<Scalar, Scalar> floor = inverse_cdf.floorEntry(reference); // less than or equal
      int sample = (Integer) floor.getValue().number();
      Scalar cumprob = floor.getKey();
      while (Scalars.lessEquals(cumprob, reference)) { // less equals
        ++sample;
        Scalar probability = p_equals(sample);
        if (Scalars.nonZero(probability)) {
          cumprob = cumprob.add(probability);
          inverse_cdf.put(cumprob, RationalScalar.of(sample, 1));
        }
        if (sample == upperBound()) {
          Scalar last = inverse_cdf.lastKey();
          if (Scalars.lessThan(last, RealScalar.ONE))
            inverse_cdf.put(RealScalar.ONE, RationalScalar.of(sample, 1));
          break;
        }
      }
      higher = inverse_cdf.higherEntry(reference); // strictly higher
    }
    return higher.getValue();
  }

  /** optional safeguard when computing CDF for probabilities with machine precision
   * 
   * @return greatest integer n for which 0 < p(n) */
  protected int upperBound() {
    return Integer.MAX_VALUE;
  }

  /* package for testing */ final NavigableMap<Scalar, Scalar> inverse_cdf() {
    return Collections.unmodifiableNavigableMap(inverse_cdf);
  }
}
