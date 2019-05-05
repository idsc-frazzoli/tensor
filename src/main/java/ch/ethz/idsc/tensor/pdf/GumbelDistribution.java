// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.Exp;
import ch.ethz.idsc.tensor.sca.Gamma;
import ch.ethz.idsc.tensor.sca.Log;
import ch.ethz.idsc.tensor.sca.Sign;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/GumbelDistribution.html">GumbelDistribution</a> */
public class GumbelDistribution extends AbstractAlphaBetaDistribution {
  private static final Scalar PISQUARED_6 = DoubleScalar.of(1.644934066848226436472415166646);

  /** parameters may be instance of {@link Quantity} with identical units
   * 
   * @param alpha any real number
   * @param beta positive
   * @return */
  public static Distribution of(Scalar alpha, Scalar beta) {
    return new GumbelDistribution(alpha, Sign.requirePositive(beta));
  }

  // ---
  private GumbelDistribution(Scalar alpha, Scalar beta) {
    super(alpha, beta);
  }

  @Override
  Scalar randomVariate(double reference) {
    // avoid result -Infinity when reference is close to 1.0
    double uniform = reference == NEXTDOWNONE //
        ? reference
        : Math.nextUp(reference);
    return alpha.add(beta.multiply(Log.FUNCTION.apply(Log.FUNCTION.apply(DoubleScalar.of(uniform)).negate())));
  }

  @Override // from MeanInterface
  public Scalar mean() {
    return alpha.subtract(Gamma.EULER.multiply(beta));
  }

  @Override // from VarianceInterface
  public Scalar variance() {
    return PISQUARED_6.multiply(beta).multiply(beta);
  }

  @Override // from PDF
  public Scalar at(Scalar x) {
    Scalar map = x.subtract(alpha).divide(beta);
    return Exp.FUNCTION.apply(map.subtract(Exp.FUNCTION.apply(map))).divide(beta);
  }

  @Override // from CDF
  public Scalar p_lessThan(Scalar x) {
    return RealScalar.ONE.subtract(Exp.FUNCTION.apply( //
        Exp.FUNCTION.apply(x.subtract(alpha).divide(beta)).negate()));
  }
}
