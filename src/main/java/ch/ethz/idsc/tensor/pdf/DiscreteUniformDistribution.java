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
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/DiscreteUniformDistribution.html">DiscreteUniformDistribution</a> */
// TODO implementation could allow the use of Quantity
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
      throw new RuntimeException(String.format("min=%d max=%d", min, max));
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
    return RationalScalar.of(max - 1 + min, 2);
  }

  @Override // from VarianceInterface
  public Scalar variance() {
    Scalar width = RationalScalar.of(max - 1 - min, 1);
    return width.multiply(RealScalar.of(2).add(width)).multiply(RationalScalar.of(1, 12));
  }

  @Override // from DiscreteDistribution
  public int lowerBound() {
    return min;
  }

  @Override // from AbstractDiscreteDistribution
  public Scalar randomVariate(Scalar reference) {
    return RationalScalar.of(min, 1).add(Floor.FUNCTION.apply(reference.multiply(RationalScalar.of(max - min, 1))));
  }

  @Override // from AbstractDiscreteDistribution
  protected Scalar protected_p_equals(int n) {
    if (max <= n)
      return RealScalar.ZERO;
    return p;
  }

  @Override // from CDF
  public Scalar p_lessThan(Scalar x) {
    Scalar num = Ceiling.FUNCTION.apply(x).subtract(RationalScalar.of(min, 1));
    return (Scalar) num.multiply(p).map(Clip.unit());
  }

  @Override // from CDF
  public Scalar p_lessEquals(Scalar x) {
    Scalar num = RealScalar.ONE.add(Floor.FUNCTION.apply(x)).subtract(RationalScalar.of(min, 1));
    return (Scalar) num.multiply(p).map(Clip.unit());
  }
}
