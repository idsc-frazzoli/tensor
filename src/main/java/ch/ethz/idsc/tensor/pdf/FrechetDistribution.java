// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.AbsSquared;
import ch.ethz.idsc.tensor.sca.Clips;
import ch.ethz.idsc.tensor.sca.Exp;
import ch.ethz.idsc.tensor.sca.Gamma;
import ch.ethz.idsc.tensor.sca.Log;
import ch.ethz.idsc.tensor.sca.Power;
import ch.ethz.idsc.tensor.sca.Sign;

/** <p>The InverseCDF at p == 1 is not defined.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/FrechetDistribution.html">FrechetDistribution</a> */
public class FrechetDistribution extends AbstractAlphaBetaDistribution implements InverseCDF {
  private static final Scalar TWO = RealScalar.of(2);

  /** @param alpha positive
   * @param beta positive, may be instance of {@link Quantity}
   * @return */
  public static Distribution of(Scalar alpha, Scalar beta) {
    if (Scalars.lessEquals(alpha, RealScalar.ZERO))
      throw TensorRuntimeException.of(alpha);
    return new FrechetDistribution(alpha, Sign.requirePositive(beta));
  }

  public static Distribution of(Number alpha, Number beta) {
    return of(RealScalar.of(alpha), RealScalar.of(beta));
  }

  // ---
  private FrechetDistribution(Scalar alpha, Scalar beta) {
    super(alpha, beta);
  }

  @Override
  protected Scalar randomVariate(double reference) {
    // avoid result -Infinity when reference is close to 1.0
    double uniform = reference == NEXTDOWNONE //
        ? reference
        : Math.nextUp(reference);
    return quantile_unit(DoubleScalar.of(uniform));
  }

  @Override // from InverseCDF
  public Scalar quantile(Scalar p) {
    return quantile_unit(Clips.unit().requireInside(p));
  }

  private Scalar quantile_unit(Scalar p) {
    return beta.multiply(Power.of(Log.FUNCTION.apply(p).negate(), alpha.reciprocal().negate()));
  }

  @Override // from MeanInterface
  public Scalar mean() {
    return Scalars.lessEquals(alpha, RealScalar.ONE) //
        ? beta.multiply(DoubleScalar.POSITIVE_INFINITY)
        : beta.multiply(Gamma.FUNCTION.apply(RealScalar.ONE.subtract(alpha.reciprocal())));
  }

  @Override // from VarianceInterface
  public Scalar variance() {
    if (Scalars.lessEquals(alpha, TWO))
      return beta.multiply(beta).multiply(DoubleScalar.POSITIVE_INFINITY);
    Scalar term = Gamma.FUNCTION.apply(RealScalar.ONE.subtract(TWO.divide(alpha)));
    return beta.multiply(beta).multiply(term).subtract(AbsSquared.FUNCTION.apply(mean()));
  }

  @Override // from PDF
  public Scalar at(Scalar x) {
    Scalar factor = Power.of(x.divide(beta), RealScalar.ONE.add(alpha).negate());
    return p_lessThan(x).multiply(alpha).multiply(factor).divide(beta);
  }

  @Override // from CDF
  public Scalar p_lessThan(Scalar x) {
    return Sign.isPositive(x) //
        ? Exp.FUNCTION.apply(Power.of(x.divide(beta), alpha.negate()).negate())
        : RealScalar.ZERO;
  }
}
