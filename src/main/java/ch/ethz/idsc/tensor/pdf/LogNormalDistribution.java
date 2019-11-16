// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.io.Serializable;
import java.util.Random;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.Exp;
import ch.ethz.idsc.tensor.sca.Log;
import ch.ethz.idsc.tensor.sca.Sign;

/** Remark: the implementation of InverseCDF is not very accurate, expect errors of 1%.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/LogNormalDistribution.html">LogNormalDistribution</a> */
public class LogNormalDistribution implements //
    ContinuousDistribution, InverseCDF, MeanInterface, VarianceInterface, Serializable {
  /** @param mu any real number
   * @param sigma any positive real number
   * @return instance of LogNormalDistribution
   * @throws Exception if sigma is zero or negative
   * @throws Exception if either parameter is of type {@link Quantity} */
  public static Distribution of(Scalar mu, Scalar sigma) {
    if (mu instanceof RealScalar && //
        sigma instanceof RealScalar)
      return new LogNormalDistribution(mu, sigma);
    throw TensorRuntimeException.of(mu, sigma);
  }

  // ---
  private final Scalar mu;
  private final NormalDistribution normalDistribution;
  private final Scalar variance;

  private LogNormalDistribution(Scalar mu, Scalar sigma) {
    this.mu = mu;
    normalDistribution = (NormalDistribution) NormalDistribution.of(mu, sigma);
    variance = normalDistribution.variance();
  }

  @Override // from CDF
  public Scalar p_lessThan(Scalar x) {
    return Sign.isPositive(x) //
        ? normalDistribution.p_lessThan(Log.FUNCTION.apply(x))
        : RealScalar.ZERO;
  }

  @Override // from CDF
  public Scalar p_lessEquals(Scalar x) {
    return p_lessThan(x);
  }

  @Override // from PDF
  public Scalar at(Scalar x) {
    return Sign.isPositive(x) //
        ? normalDistribution.at(Log.FUNCTION.apply(x)).divide(x)
        : RealScalar.ZERO;
  }

  @Override // from RandomVariateInterface
  public Scalar randomVariate(Random random) {
    return Exp.FUNCTION.apply(normalDistribution.randomVariate(random));
  }

  @Override // from MeanInterface
  public Scalar mean() {
    return Exp.FUNCTION.apply(variance.multiply(RationalScalar.HALF).add(mu));
  }

  @Override // from VarianceInterface
  public Scalar variance() {
    return Exp.FUNCTION.apply(variance.add(mu).add(mu)).multiply( //
        Exp.FUNCTION.apply(variance).subtract(RealScalar.ONE));
  }

  @Override // from InverseCDF
  public Scalar quantile(Scalar p) {
    return Exp.FUNCTION.apply(normalDistribution.quantile(p));
  }

  @Override // from Object
  public String toString() {
    return "Log" + normalDistribution.toString();
  }
}
