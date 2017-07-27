// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.sca.Exp;
import ch.ethz.idsc.tensor.sca.Gamma;
import ch.ethz.idsc.tensor.sca.Power;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ErlangDistribution.html">ErlangDistribution</a> */
public class ErlangDistribution implements Distribution, MeanInterface, PDF, VarianceInterface {
  // TODO provide more interfaces
  /** @param k
   * @param lambda
   * @return */
  public static Distribution of(int k, Scalar lambda) {
    if (k <= 0)
      throw new RuntimeException("k=" + k);
    return new ErlangDistribution(RealScalar.of(k), lambda);
  }

  private final Scalar k;
  private final Scalar lambda;
  private final Scalar factor;

  private ErlangDistribution(Scalar k, Scalar lambda) {
    this.k = k;
    this.lambda = lambda;
    factor = Power.of(lambda, k).divide(Gamma.of(k));
  }

  @Override
  public Scalar at(Scalar x) {
    if (Scalars.lessEquals(x, RealScalar.ZERO))
      return RealScalar.ZERO;
    return Exp.of(x.negate().multiply(lambda)) //
        .multiply(Power.of(x, k.subtract(RealScalar.ONE))).multiply(factor);
  }

  @Override
  public Scalar mean() {
    return k.divide(lambda);
  }

  @Override
  public Scalar variance() {
    return k.divide(lambda.multiply(lambda));
  }
}
