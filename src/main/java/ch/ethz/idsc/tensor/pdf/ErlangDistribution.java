// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.Exp;
import ch.ethz.idsc.tensor.sca.Factorial;
import ch.ethz.idsc.tensor.sca.Power;
import ch.ethz.idsc.tensor.sca.Sign;

/** ErlangDistribution[k, lambda] == GammaDistribution[k, 1 / lambda]
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ErlangDistribution.html">ErlangDistribution</a> */
public class ErlangDistribution implements Distribution, MeanInterface, PDF, VarianceInterface {
  /** @param k positive integer
   * @param lambda, may be instance of {@link Quantity}
   * @return */
  public static Distribution of(int k, Scalar lambda) {
    if (k <= 0)
      throw new RuntimeException("k=" + k);
    return new ErlangDistribution(k, lambda);
  }

  private final Scalar k;
  private final Scalar lambda;
  private final Scalar factor;

  private ErlangDistribution(int k, Scalar lambda) {
    this.k = RationalScalar.of(k, 1);
    this.lambda = lambda;
    factor = Power.of(lambda, k).divide(Factorial.of(k - 1));
  }

  @Override // from PDF
  public Scalar at(Scalar x) {
    if (Sign.isNegativeOrZero(x))
      return lambda.zero();
    return Exp.FUNCTION.apply(x.negate().multiply(lambda)) //
        .multiply(Power.of(x, k.subtract(RealScalar.ONE))).multiply(factor);
  }

  @Override // from MeanInterface
  public Scalar mean() {
    return k.divide(lambda);
  }

  @Override // from VarianceInterface
  public Scalar variance() {
    return k.divide(lambda.multiply(lambda));
  }

  @Override // from Object
  public String toString() {
    return String.format("%s[%s, %s]", getClass().getSimpleName(), k, lambda);
  }
}
