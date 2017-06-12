// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.sca.Power;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/GeometricDistribution.html">GeometricDistribution</a> */
public class GeometricDistribution extends AbstractDiscreteDistribution {
  /** @param p with 0 < p < 1 */
  public static Distribution of(Scalar p) {
    if (Scalars.lessEquals(p, RealScalar.ZERO) || Scalars.lessEquals(RealScalar.ONE, p))
      throw TensorRuntimeException.of(p);
    return new GeometricDistribution(p);
  }

  // ---
  private final Scalar p;

  private GeometricDistribution(Scalar p) {
    this.p = p;
  }

  @Override // from DiscreteDistribution
  public int lowerBound() {
    return 0;
  }

  @Override // from DiscreteDistribution
  public Scalar p_equals(int n) {
    return p.multiply(Power.of(RealScalar.ONE.subtract(p), n));
  }

  @Override // from Distribution
  public Scalar mean() {
    return p.invert().subtract(RealScalar.ONE);
  }

  @Override // from Distribution
  public Scalar variance() {
    return RealScalar.ONE.subtract(p).divide(p.multiply(p));
  }
}
