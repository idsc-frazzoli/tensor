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

/** functionality and suggested base class for a discrete probability distribution */
public abstract class AbstractDiscreteDistribution implements DiscreteDistribution, PDF {
  // inverse cdf maps from probability to sample and is built during random sampling generation
  private final NavigableMap<Scalar, Scalar> inverse_cdf = new TreeMap<>();

  @Override // from RandomVariateInterface
  public synchronized Scalar randomVariate(Random random) {
    if (inverse_cdf.isEmpty())
      inverse_cdf.put(p_equals(lowerBound()), RealScalar.of(lowerBound()));
    // ---
    Scalar reference = RealScalar.of(random.nextDouble());
    Entry<Scalar, Scalar> higher = inverse_cdf.higherEntry(reference); // strictly higher
    if (higher == null) {
      Entry<Scalar, Scalar> lower = inverse_cdf.floorEntry(reference); // less than or equal
      int sample = lower.getValue().number().intValue();
      Scalar cumprob = lower.getKey();
      while (Scalars.lessThan(cumprob, reference)) {
        ++sample;
        cumprob = cumprob.add(p_equals(sample));
        inverse_cdf.put(cumprob, RealScalar.of(sample));
      }
      higher = inverse_cdf.higherEntry(reference); // strictly higher
    }
    return higher.getValue();
  }

  /* package for testing */ NavigableMap<Scalar, Scalar> inverse_cdf() {
    return Collections.unmodifiableNavigableMap(inverse_cdf);
  }

  @Override // from PDF
  public final Scalar p_equals(Scalar x) {
    if (!IntegerQ.of(x))
      return RealScalar.ZERO;
    int k = Scalars.intValueExact(x);
    return p_equals(k);
  }
}
