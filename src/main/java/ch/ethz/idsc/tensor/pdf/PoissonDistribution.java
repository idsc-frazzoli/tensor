// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Last;
import ch.ethz.idsc.tensor.sca.Exp;

/** in Mathematica, the CDF of the Poisson-distribution is expressed as
 * 
 * CDF[PoissonDistribution[lambda], x] == GammaRegularized[1 + Floor[x], lambda]
 * 
 * inspired by
 * <a href="https://reference.wolfram.com/language/ref/PoissonDistribution.html">PoissonDistribution</a> */
public class PoissonDistribution extends AbstractDiscreteDistribution implements VarianceInterface {
  // lambda above max lead to incorrect results due to numerical properties
  private static final Scalar LAMBDA_MAX = RealScalar.of(700);

  /** Example:
   * PDF[PoissonDistribution[Lambda], 2] == 1/(3!) Exp[-Lambda] Lambda^3
   * 
   * @param lambda positive and <= 700
   * @return */
  public static Distribution of(Scalar lambda) {
    if (Scalars.lessEquals(lambda, RealScalar.ZERO))
      throw TensorRuntimeException.of(lambda);
    if (Scalars.lessThan(LAMBDA_MAX, lambda))
      throw TensorRuntimeException.of(lambda);
    return new PoissonDistribution(lambda);
  }

  // ---
  private final Scalar lambda;
  private final Tensor values = Tensors.empty();

  private PoissonDistribution(Scalar lambda) {
    this.lambda = lambda;
    values.append(Exp.of(lambda.negate()));
  }

  @Override // from MeanInterface
  public Scalar mean() {
    return lambda;
  }

  @Override // from VarianceInterface
  public Scalar variance() {
    return lambda;
  }

  @Override // from DiscreteDistribution
  public int lowerBound() {
    return 0;
  }

  @Override // from DiscreteDistribution
  public Scalar p_equals(int n) {
    if (n < 0)
      return RealScalar.ZERO;
    while (values.length() <= n) {
      Scalar factor = lambda.divide(RealScalar.of(values.length()));
      values.append(Last.of(values).multiply(factor));
    }
    return values.Get(n);
  }
}
