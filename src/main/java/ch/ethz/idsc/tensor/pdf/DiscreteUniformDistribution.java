// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/DiscreteUniformDistribution.html">DiscreteUniformDistribution</a> */
public class DiscreteUniformDistribution implements DiscreteDistribution {
  /** Example:
   * PDF[DiscreteUniformDistribution[{0, 10}], x] == 1/11 for 0 <= x <=10 and x integer
   * 
   * @param min inclusive
   * @param max inclusive */
  public static DiscreteDistribution of(Scalar min, Scalar max) {
    if (Scalars.lessThan(max, min))
      throw TensorRuntimeException.of(min, max);
    return new DiscreteUniformDistribution(min, max);
  }

  // ---
  private final int min;
  private final int max;
  private final Scalar p;

  private DiscreteUniformDistribution(Scalar min, Scalar max) {
    this.min = Scalars.intValueExact(min);
    this.max = Scalars.intValueExact(max);
    p = max.subtract(min).add(RealScalar.ONE).invert();
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
}
