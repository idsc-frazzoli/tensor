// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.io.Serializable;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.Clip;
import ch.ethz.idsc.tensor.sca.Clips;

/** uniform distribution over continuous interval [a, b].
 * 
 * <p>InverseCDF is defined over interval [0, 1]
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/UniformDistribution.html">UniformDistribution</a> */
public class UniformDistribution extends AbstractContinuousDistribution implements //
    InverseCDF, MeanInterface, VarianceInterface, Serializable {
  private static final Scalar _12 = RealScalar.of(12);
  private static final Distribution UNIT = new UniformDistribution(Clips.unit()) {
    @Override // from AbstractContinuousDistribution
    public Scalar randomVariate(double reference) {
      return DoubleScalar.of(reference);
    }
  };

  /** the input parameters may be instance of {@link Quantity} of identical unit
   * 
   * @param min < max
   * @param max
   * @return uniform distribution over the half-open interval [min, max) */
  public static Distribution of(Scalar min, Scalar max) {
    return new UniformDistribution(Clips.interval(min, max));
  }

  /** @param min < max
   * @param max
   * @return uniform distribution over the half-open interval [min, max) */
  public static Distribution of(Number min, Number max) {
    return new UniformDistribution(Clips.interval(min, max));
  }

  /** @param clip
   * @return uniform distribution over the half-open interval [clip.min(), clip.max()) */
  public static Distribution of(Clip clip) {
    return new UniformDistribution(clip);
  }

  /** @return uniform distribution over the half-open unit interval [0, 1) */
  public static Distribution unit() {
    return UNIT;
  }

  // ---
  private final Clip clip;
  private final Scalar width;

  private UniformDistribution(Clip clip) {
    this.clip = clip;
    width = clip.width();
    if (Scalars.isZero(width))
      throw TensorRuntimeException.of(clip.min(), clip.max());
  }

  @Override // from AbstractContinuousDistribution
  protected Scalar randomVariate(double reference) {
    return quantile_unit(DoubleScalar.of(reference));
  }

  @Override // from InverseCDF
  public Scalar quantile(Scalar p) {
    return quantile_unit(Clips.unit().requireInside(p));
  }

  private Scalar quantile_unit(Scalar p) {
    return p.multiply(width).add(clip.min());
  }

  @Override // from MeanInterface
  public Scalar mean() {
    return clip.min().add(width.multiply(RationalScalar.HALF));
  }

  @Override // from VarianceInterface
  public Scalar variance() {
    return width.multiply(width).divide(_12);
  }

  @Override // from PDF
  public Scalar at(Scalar x) {
    return clip.isInside(x) //
        ? width.reciprocal()
        : RealScalar.ZERO;
  }

  @Override // from CDF
  public Scalar p_lessThan(Scalar x) {
    return clip.rescale(x);
  }

  @Override // from Object
  public String toString() {
    return String.format("%s[%s, %s]", getClass().getSimpleName(), clip.min(), clip.max());
  }
}
