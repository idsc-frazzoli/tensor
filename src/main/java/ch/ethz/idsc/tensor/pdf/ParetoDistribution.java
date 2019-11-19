// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.sca.Power;
import ch.ethz.idsc.tensor.sca.Sign;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ParetoDistribution.html">ParetoDistribution</a> */
public class ParetoDistribution extends AbstractContinuousDistribution implements MeanInterface, VarianceInterface {
  private static final Scalar TWO = RealScalar.of(2);

  /** @param k strictly positive real number
   * @param alpha strictly positive real number
   * @return */
  public static Distribution of(Scalar k, Scalar alpha) {
    if (Scalars.lessThan(RealScalar.ZERO, k))
      return new ParetoDistribution(k, Sign.requirePositive(alpha));
    throw TensorRuntimeException.of(k);
  }

  // ---
  private final Scalar k;
  private final Scalar alpha;
  private final Scalar k_alpha;

  private ParetoDistribution(Scalar k, Scalar alpha) {
    this.k = k;
    this.alpha = alpha;
    k_alpha = Power.of(k, alpha);
  }

  @Override // from CDF
  public Scalar p_lessThan(Scalar x) {
    return Scalars.lessEquals(k, x) //
        ? RealScalar.ONE.subtract(Power.of(k.divide(x), alpha))
        : RealScalar.ZERO;
  }

  @Override // from PDF
  public Scalar at(Scalar x) {
    return Scalars.lessEquals(k, x) //
        ? k_alpha.divide(Power.of(x, alpha.add(RealScalar.ONE))).multiply(alpha)
        : RealScalar.ZERO;
  }

  @Override // from AbstractContinuousDistribution
  protected Scalar randomVariate(double reference) {
    return k.divide(Power.of(reference, alpha.reciprocal()));
  }

  @Override // from MeanInterface
  public Scalar mean() {
    return Scalars.lessThan(RealScalar.ONE, alpha) //
        ? k.multiply(alpha).divide(alpha.subtract(RealScalar.ONE))
        : DoubleScalar.INDETERMINATE;
  }

  @Override // from VarianceInterface
  public Scalar variance() {
    if (Scalars.lessThan(TWO, alpha)) {
      Scalar amo = alpha.subtract(RealScalar.ONE);
      return k.multiply(k).multiply(alpha).divide(alpha.subtract(TWO).multiply(amo).multiply(amo));
    }
    return DoubleScalar.INDETERMINATE;
  }
}
