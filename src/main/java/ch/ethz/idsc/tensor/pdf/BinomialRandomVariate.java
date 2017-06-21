// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;

/** robust function to generate random variates for any parameters n and p
 * 
 * the complexity of the algorithm is O(n)
 * 
 * extension due to Claudio Ruch */
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
    double p_double = p.number().doubleValue();
    for (int index = 0; index < n; ++index)
      if (random.nextDouble() < p_double)
        ++k;
    return RealScalar.of(k);
  }
}
