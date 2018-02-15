// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.Clip;

/** uniform distribution over continuous interval [a, b].
 * 
 * <p>InverseCDF is defined over interval [0, 1]
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/UniformDistribution.html">UniformDistribution</a> */
public class UniformDistribution extends AbstractContinuousDistribution implements //
    InverseCDF, MeanInterface, VarianceInterface {
  private static final Distribution UNIT = new UniformDistribution(RealScalar.ZERO, RealScalar.ONE) {
    @Override // from RandomVariateInterface
    public Scalar randomVariate(Random random) {
      return DoubleScalar.of(random.nextDouble());
    }
  };

  /** the input parameters may be instance of {@link Quantity} of identical unit
   * 
   * @param min < max
   * @param max
   * @return uniform distribution over the half-open interval [min, max) */
  public static Distribution of(Scalar min, Scalar max) {
    if (Scalars.lessEquals(max, min))
      throw TensorRuntimeException.of(min, max);
    return new UniformDistribution(min, max);
  }

  /** @param min < max
   * @param max
   * @return uniform distribution over the half-open interval [min, max) */
  public static Distribution of(Number min, Number max) {
    return of(RealScalar.of(min), RealScalar.of(max));
  }

  /** @return uniform distribution over the half-open unit interval [0, 1) */
  public static Distribution unit() {
    return UNIT;
  }

  // ---
  private final Clip clip;
  private final Scalar width;

  private UniformDistribution(Scalar min, Scalar max) {
    clip = Clip.function(min, max);
    width = clip.width();
  }

  @Override // from RandomVariateInterface
  public Scalar randomVariate(Random random) {
    return quantile_unit(DoubleScalar.of(random.nextDouble()));
  }

  @Override // from InverseCDF
  public Scalar quantile(Scalar p) {
    return quantile_unit(Clip.unit().requireInside(p));
  }

  private Scalar quantile_unit(Scalar p) {
    return p.multiply(width).add(clip.min());
  }

  @Override // from MeanInterface
  public Scalar mean() {
    return clip.min().add(width.divide(RationalScalar.of(2, 1)));
  }

  @Override // from VarianceInterface
  public Scalar variance() {
    return width.multiply(width).divide(RationalScalar.of(12, 1));
  }

  @Override // from PDF
  public Scalar at(Scalar x) {
    return clip.isInside(x) ? width.reciprocal() : RealScalar.ZERO;
  }

  @Override // from CDF
  public Scalar p_lessThan(Scalar x) {
    return clip.rescale(x);
  }

  @Override // from CDF
  public Scalar p_lessEquals(Scalar x) {
    return p_lessThan(x);
  }

  @Override // from Object
  public String toString() {
    return String.format("%s[%s, %s]", getClass().getSimpleName(), clip.min(), clip.max());
  }
}
