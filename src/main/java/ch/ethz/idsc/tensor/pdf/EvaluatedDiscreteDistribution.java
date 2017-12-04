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
import ch.ethz.idsc.tensor.sca.Clip;

/** functionality and suggested base class for a discrete probability distribution */
public abstract class EvaluatedDiscreteDistribution extends AbstractDiscreteDistribution {
  /** inverse cdf maps from probability to integers and is built during random sampling generation.
   * the value type of the map is Scalar (instead of Integer) to reuse the instances of Scalar */
  private final NavigableMap<Scalar, Scalar> inverse_cdf = new TreeMap<>();

  /** @param p in the half-open interval [0, 1)
   * @return */
  @Override // from InverseCDF
  public final synchronized Scalar quantile(Scalar p) {
    // if the input is outside the valid range, the while loop below may never terminate
    Clip.unit().isInsideElseThrow(p);
    // ---
    if (inverse_cdf.isEmpty())
      inverse_cdf.put(p_equals(lowerBound()), RationalScalar.of(lowerBound(), 1));
    // ---
    Entry<Scalar, Scalar> higher = lookup(p);
    if (Objects.isNull(higher)) {
      Entry<Scalar, Scalar> floor = inverse_cdf.floorEntry(p); // less than or equal
      int sample = (Integer) floor.getValue().number();
      Scalar cumprob = floor.getKey();
      while (Scalars.lessEquals(cumprob, p)) { // less equals
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
      higher = lookup(p);
    }
    return higher.getValue();
  }

  private Entry<Scalar, Scalar> lookup(Scalar p) {
    return Scalars.lessThan(p, RealScalar.ONE) //
        ? inverse_cdf.higherEntry(p) // strictly higher
        : inverse_cdf.ceilingEntry(p);
  }

  /** optional safeguard when computing CDF for probabilities with machine precision
   * 
   * @return greatest integer n for which 0 < p(n) */
  protected abstract int upperBound();

  /* package for testing */ final NavigableMap<Scalar, Scalar> inverse_cdf() {
    return Collections.unmodifiableNavigableMap(inverse_cdf);
  }
}
