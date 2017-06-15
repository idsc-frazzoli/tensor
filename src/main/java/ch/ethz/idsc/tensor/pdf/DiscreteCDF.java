// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.Function;

import ch.ethz.idsc.tensor.MachineNumberQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;

/* package */ class DiscreteCDF extends DiscretePDF implements CDF {
  // 0.9999999999999999
  // .^....^....^....^.
  /* package for testing */ static final Scalar CDF_NUMERIC_THRESHOLD = RealScalar.of(1e-14);
  // ---
  private final NavigableMap<Scalar, Scalar> cdf = new TreeMap<>();
  private boolean cdf_finished = false;

  DiscreteCDF(DiscreteDistribution discreteDistribution) {
    super(discreteDistribution);
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
      cdf.put(RealScalar.of(first), discreteDistribution.p_equals(first));
    }
    Entry<Scalar, Scalar> ceiling = cdf.ceilingEntry(x);
    if (cdf_finished || ceiling != null) {
      Entry<Scalar, Scalar> entry = function.apply(x);
      return entry == null ? RealScalar.ZERO : entry.getValue();
    }
    // <- ceiling == null, now integrate until finished or ceiling of x exists
    Entry<Scalar, Scalar> last = cdf.lastEntry();
    int k = Scalars.intValueExact(last.getKey());
    Scalar cumprob = last.getValue();
    while (Scalars.lessEquals(RationalScalar.of(k, 1), x) && !cdf_finished) {
      ++k;
      Scalar delta = discreteDistribution.p_equals(k);
      cumprob = cumprob.add(delta);
      cdf.put(RealScalar.of(k), cumprob);
      cdf_finished |= cumprob.equals(RealScalar.ONE);
      cdf_finished |= MachineNumberQ.of(cumprob) && //
          delta.equals(RealScalar.ZERO) && //
          Scalars.lessThan(cumprob.subtract(RealScalar.ONE).abs(), CDF_NUMERIC_THRESHOLD);
    }
    return p_function(x, function);
  }

  /* package for testing */ boolean cdf_finished() {
    return cdf_finished;
  }
}
