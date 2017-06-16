// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.sca.Clip;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/UniformDistribution.html">UniformDistribution</a> */
public class UniformDistribution implements ContinuousDistribution {
  /** @param min < max
   * @param max
   * @return uniform distribution over the half-open interval [min, max) */
  public static Distribution of(Scalar min, Scalar max) {
    if (Scalars.lessEquals(max, min))
      throw TensorRuntimeException.of(min, max);
    return new UniformDistribution(min, max);
  }

  /** @return uniform distribution over the half-open unit interval [0, 1) */
  public static Distribution unit() {
    return of(RealScalar.ZERO, RealScalar.ONE);
  }

  private static final Clip CLIP = Clip.function(0, 1);
  // ---
  private final Scalar min;
  private final Scalar width;

  private UniformDistribution(Scalar min, Scalar max) {
    this.min = min;
    width = max.subtract(min);
  }

  @Override // from RandomVariateInterface
  public Scalar randomVariate(Random random) {
    return RealScalar.of(random.nextDouble()).multiply(width).add(min);
  }

  @Override // from Distribution
  public Scalar mean() {
    return min.add(width.multiply(RationalScalar.of(1, 2)));
  }

  @Override // from Distribution
  public Scalar variance() {
    return width.multiply(width).multiply(RationalScalar.of(1, 12));
  }

  @Override // from ContinuousDistribution
  public Scalar p_lessThan(Scalar x) {
    return CLIP.apply(x.subtract(min).divide(width));
  }

  @Override // from ContinuousDistribution
  public Scalar p_lessEquals(Scalar x) {
    return p_lessThan(x);
  }
}
