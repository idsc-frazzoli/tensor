// code by cr and jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;

/** fallback option to robustly generate random variates from a
 * {@link BinomialDistribution} for any parameters n and p.
 * The complexity of the generation is O(n).
 * 
 * For large n, and p away from 0, or 1, the option to approximate the
 * distribution as a {@link NormalDistribution} should be considered.
 * 
 * implementation by Claudio Ruch */
/* package */ class BinomialRandomVariate implements Distribution, //
    MeanInterface, RandomVariateInterface, VarianceInterface {
  private final int n;
  private final Scalar p;

  /* package */ BinomialRandomVariate(int n, Scalar p) {
    this.n = n;
    this.p = p;
  }

  @Override // from RandomVariateInterface
  public Scalar randomVariate(Random random) {
    double p_success = p.number().doubleValue();
    return RationalScalar.of(IntStream.range(0, n) //
        .filter(i -> random.nextDouble() < p_success).count(), 1);
  }

  @Override // from MeanInterface
  public Scalar mean() {
    return RationalScalar.of(n, 1).multiply(p);
  }

  @Override // from VarianceInterface
  public Scalar variance() {
    return mean().multiply(RealScalar.ONE.subtract(p));
  }
}
