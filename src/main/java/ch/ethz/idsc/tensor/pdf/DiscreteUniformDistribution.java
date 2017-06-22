// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.sca.Ceiling;
import ch.ethz.idsc.tensor.sca.Clip;
import ch.ethz.idsc.tensor.sca.Floor;

/** Careful:
 * In Mathematica::DiscreteUniformDistribution the upper bound "max" is inclusive.
 * The tensor library considers "max" to be excluded.
 * 
 * inspired by
 * <a href="https://reference.wolfram.com/language/ref/DiscreteUniformDistribution.html">DiscreteUniformDistribution</a> */
public class DiscreteUniformDistribution extends AbstractDiscreteDistribution implements CDF, VarianceInterface {
  /** Example:
   * PDF[DiscreteUniformDistribution[{0, 10}], x] == 1/10 for 0 <= x < 10 and x integer
   * 
   * @param min inclusive
   * @param max exclusive and min < max
   * @return distribution */
  public static Distribution of(Scalar min, Scalar max) {
    return of(Scalars.intValueExact(min), Scalars.intValueExact(max));
  }

  /** @param min inclusive
   * @param max exclusive and min < max
   * @return distribution */
  public static Distribution of(int min, int max) {
    if (max <= min)
      throw new RuntimeException();
    return new DiscreteUniformDistribution(min, max);
  }

  // ---
  private final int min; // inclusive
  private final int max; // exclusive
  private final Scalar p; // precomputed

  private DiscreteUniformDistribution(int min, int max) {
    this.min = min;
    this.max = max;
    p = RationalScalar.of(1, max - min);
  }

  @Override // from MeanInterface
  public Scalar mean() {
    return RealScalar.of(max - 1 + min).multiply(RationalScalar.of(1, 2));
  }

  @Override // from VarianceInterface
  public Scalar variance() {
    Scalar width = RealScalar.of(max - 1 - min);
    return width.multiply(RealScalar.of(2).add(width)).multiply(RationalScalar.of(1, 12));
  }

  @Override // from DiscreteDistribution
  public int lowerBound() {
    return min;
  }

  @Override // from AbstractDiscreteDistribution
  protected int upperBound() {
    return max - 1;
  }

  @Override // from AbstractDiscreteDistribution
  protected Scalar protected_p_equals(int n) {
    if (max <= n)
      return RealScalar.ZERO;
    return p;
  }

  @Override // from CDF
  public Scalar p_lessThan(Scalar x) {
    Scalar num = Ceiling.of(x).subtract(RealScalar.of(min));
    return (Scalar) num.multiply(p).map(Clip.UNIT);
  }

  @Override // from CDF
  public Scalar p_lessEquals(Scalar x) {
    Scalar num = RealScalar.ONE.add(Floor.of(x)).subtract(RealScalar.of(min));
    return (Scalar) num.multiply(p).map(Clip.UNIT);
  }
}
