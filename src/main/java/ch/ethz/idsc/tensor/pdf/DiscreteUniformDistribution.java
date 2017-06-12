// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/DiscreteUniformDistribution.html">DiscreteUniformDistribution</a> */
public class DiscreteUniformDistribution extends AbstractDiscreteDistribution {
  /** Example:
   * PDF[DiscreteUniformDistribution[{0, 10}], x] == 1/11 for 0 <= x <=10 and x integer
   * 
   * @param min inclusive
   * @param max inclusive and min < max
   * @return distribution */
  public static Distribution of(Scalar min, Scalar max) {
    return of(Scalars.intValueExact(min), Scalars.intValueExact(max));
  }

  /** @param min inclusive
   * @param max inclusive and min < max
   * @return distribution */
  public static Distribution of(int min, int max) {
    if (max < min)
      throw new RuntimeException();
    return new DiscreteUniformDistribution(min, max);
  }

  // ---
  private final int min;
  private final int max;
  private final Scalar p;

  private DiscreteUniformDistribution(int min, int max) {
    this.min = min;
    this.max = max;
    p = RationalScalar.of(1, max - min + 1);
  }

  @Override // from DiscreteDistribution
  public int lowerBound() {
    return min;
  }

  @Override // from DiscreteDistribution
  public Scalar p_equals(int n) {
    if (n < min || max < n)
      return RealScalar.ZERO;
    return p;
  }

  @Override // from Distribution
  public Scalar mean() {
    return RealScalar.of(max + min).multiply(RationalScalar.of(1, 2));
  }

  @Override // from Distribution
  public Scalar variance() {
    Scalar width = RealScalar.of(max - min);
    return width.multiply(RealScalar.of(2).add(width)).multiply(RationalScalar.of(1, 12));
  }
}
