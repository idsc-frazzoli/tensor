// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.MachineNumberQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.sca.Power;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/GeometricDistribution.html">GeometricDistribution</a> */
public class GeometricDistribution extends AbstractDiscreteDistribution {
  // lambda above max lead to incorrect results due to numerical properties
  private static final Scalar P_MIN = RealScalar.of(0.01);

  /** @param p with 0 < p < 1 denotes probability P(X==0) == p
   * if p is in machine precision, the additional requirement 0.01 <= p has to hold */
  public static Distribution of(Scalar p) {
    if (Scalars.lessEquals(p, RealScalar.ZERO) || Scalars.lessEquals(RealScalar.ONE, p))
      throw TensorRuntimeException.of(p);
    if (MachineNumberQ.of(p) && Scalars.lessThan(p, P_MIN))
      throw TensorRuntimeException.of(p);
    return new GeometricDistribution(p);
  }

  // ---
  private final Scalar p;

  private GeometricDistribution(Scalar p) {
    this.p = p;
  }

  @Override // from Distribution
  public Scalar mean() {
    return p.invert().subtract(RealScalar.ONE);
  }

  @Override // from Distribution
  public Scalar variance() {
    return RealScalar.ONE.subtract(p).divide(p.multiply(p));
  }

  @Override // from DiscreteDistribution
  public int lowerBound() {
    return 0;
  }

  @Override // from DiscreteDistribution
  public Scalar p_equals(int n) {
    // PDF[GeometricDistribution[p], n] == (1 - p) ^ n * p
    return p.multiply(Power.of(RealScalar.ONE.subtract(p), n));
  }
}
