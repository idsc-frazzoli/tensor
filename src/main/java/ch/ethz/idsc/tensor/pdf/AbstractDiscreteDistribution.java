// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Collections;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

import ch.ethz.idsc.tensor.IntegerQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** functionality and suggested base class for a discrete probability distribution */
public abstract class AbstractDiscreteDistribution implements DiscreteDistribution, //
    MeanInterface, PDF, RandomVariateInterface {
  /** inverse cdf maps from probability to integers and is built during random sampling generation.
   * the value type of the map is Scalar (instead of Integer) to reuse the instances of Scalar */
  private final NavigableMap<Scalar, Scalar> inverse_cdf = new TreeMap<>();

  @Override // from RandomVariateInterface
  public final Scalar randomVariate(Random random) {
    return randomVariate(RealScalar.of(random.nextDouble()));
  }

  /** @param reference in the half-open interval [0, 1)
   * @return */
  /* package for testing */ synchronized final Scalar randomVariate(Scalar reference) {
    // if the input is outside the valid range, the while loop below may never terminate
    if (Scalars.lessThan(reference, RealScalar.ZERO) || Scalars.lessEquals(RealScalar.ONE, reference))
      throw TensorRuntimeException.of(reference);
    // ---
    if (inverse_cdf.isEmpty())
      inverse_cdf.put(p_equals(lowerBound()), RealScalar.of(lowerBound()));
    // ---
    Entry<Scalar, Scalar> higher = inverse_cdf.higherEntry(reference); // strictly higher
    if (higher == null) {
      Entry<Scalar, Scalar> floor = inverse_cdf.floorEntry(reference); // less than or equal
      int sample = (Integer) floor.getValue().number();
      Scalar cumprob = floor.getKey();
      while (Scalars.lessEquals(cumprob, reference)) { // less equals
        ++sample;
        Scalar probability = p_equals(sample);
        if (Scalars.nonZero(probability)) {
          cumprob = cumprob.add(probability);
          inverse_cdf.put(cumprob, RealScalar.of(sample));
        }
      }
      higher = inverse_cdf.higherEntry(reference); // strictly higher
    }
    return higher.getValue();
  }

  /* package for testing */ final NavigableMap<Scalar, Scalar> inverse_cdf() {
    return Collections.unmodifiableNavigableMap(inverse_cdf);
  }

  @Override // from PDF
  public final Scalar at(Scalar x) {
    if (!IntegerQ.of(x))
      return RealScalar.ZERO;
    int n = Scalars.intValueExact(x);
    return p_equals(n);
  }

  @Override // from DiscreteDistribution
  public final Scalar p_equals(int n) {
    if (n < lowerBound())
      return RealScalar.ZERO;
    return protected_p_equals(n);
  }

  /** @param n with n >= lowerBound()
   * @return P(X == n), i.e. probability of random variable X == n */
  protected abstract Scalar protected_p_equals(int n);
}
