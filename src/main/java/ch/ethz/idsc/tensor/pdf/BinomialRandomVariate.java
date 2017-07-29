// code by cr and jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;

/** fallback option to robustly generate random variates from a
 * {@link BinomialDistribution} for any parameters n and p.
 * The complexity of the generation is O(n).
 * 
 * For large n, and p away from 0, or 1, the option to approximate the
 * distribution as a {@link NormalDistribution} should be considered.
 * 
 * implementation by Claudio Ruch */
/* package */ class BinomialRandomVariate implements Distribution, RandomVariateInterface {
  private final int n;
  private final Scalar p;

  /* package */ BinomialRandomVariate(int n, Scalar p) {
    this.n = n;
    this.p = p;
  }

  /** @param random
   * @param n
   * @param p
   * @return */
  @Override // from RandomVariateInterface
  public Scalar randomVariate(Random random) {
    int k = 0;
    double p_success = p.number().doubleValue();
    for (int index = 0; index < n; ++index)
      if (random.nextDouble() < p_success)
        ++k;
    return RationalScalar.of(k, 1);
  }
}
