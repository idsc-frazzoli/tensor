// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Function;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;

/** class performs the integration of probabilities to calculate the cumulative distribution function
 * whenever there is no closed form expression for the terms. */
/* package */ class DiscreteCDF implements CDF {
  // 0.9999999999999999
  // .^....^....^....^.
  /* package for testing */ static final Scalar CDF_NUMERIC_THRESHOLD = DoubleScalar.of(1e-14);
  // ---
  private final DiscreteDistribution discreteDistribution;
  private final NavigableMap<Scalar, Scalar> cdf = new TreeMap<>();
  private boolean cdf_finished = false;

  DiscreteCDF(DiscreteDistribution discreteDistribution) {
    this.discreteDistribution = discreteDistribution;
  }

  @Override // from CDF
  public Scalar p_lessThan(Scalar x) {
    return p_function(x, scalar -> cdf.lowerEntry(scalar));
  }

  @Override // from CDF
  public Scalar p_lessEquals(Scalar x) {
    return p_function(x, scalar -> cdf.floorEntry(scalar));
  }

  // helper function
  private Scalar p_function(Scalar x, Function<Scalar, Entry<Scalar, Scalar>> function) {
    if (cdf.isEmpty()) {
      int first = discreteDistribution.lowerBound();
      cdf.put(RationalScalar.of(first, 1), discreteDistribution.p_equals(first));
    }
    Entry<Scalar, Scalar> ceiling = cdf.ceilingEntry(x);
    if (cdf_finished || Objects.nonNull(ceiling)) {
      Entry<Scalar, Scalar> entry = function.apply(x);
      return Objects.isNull(entry) ? RealScalar.ZERO : entry.getValue();
    }
    // <- ceiling == null, now integrate until finished or ceiling of x exists
    Entry<Scalar, Scalar> last = cdf.lastEntry();
    int k = Scalars.intValueExact(last.getKey());
    Scalar cumprob = last.getValue();
    while (Scalars.lessEquals(RationalScalar.of(k, 1), x) && !cdf_finished) {
      ++k;
      Scalar p_equals = discreteDistribution.p_equals(k);
      cumprob = cumprob.add(p_equals);
      cdf.put(RationalScalar.of(k, 1), cumprob);
      cdf_finished |= isFinished(p_equals, cumprob);
    }
    return p_function(x, function);
  }

  // also used in Expectation
  /* package */ static boolean isFinished(Scalar p_equals, Scalar cumprob) {
    boolean finished = false;
    finished |= cumprob.equals(RealScalar.ONE);
    finished |= !ExactScalarQ.of(cumprob) && //
        p_equals.equals(RealScalar.ZERO) && //
        Scalars.lessThan(cumprob.subtract(RealScalar.ONE).abs(), CDF_NUMERIC_THRESHOLD);
    return finished;
  }

  /* package for testing */ boolean cdf_finished() {
    return cdf_finished;
  }
}
