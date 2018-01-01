// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RationalScalar;
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
public class PoissonDistribution extends EvaluatedDiscreteDistribution implements VarianceInterface {
  // lambda above max lead to incorrect results due to numerical properties
  private static final int P_EQUALS_MAX = 1950; // probabilities are zero beyond that point
  private static final Scalar LAMBDA_MAX = RealScalar.of(700);

  /** Example:
   * PDF[PoissonDistribution[lambda], n] == 1/(n!) Exp[-lambda] lambda^n
   * 
   * Because P[X==0] == Exp[-lambda], the implementation limits lambda to 700.
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
    values.append(Exp.FUNCTION.apply(lambda.negate()));
    inverse_cdf_build();
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

  @Override // from EvaluatedDiscreteDistribution
  protected int upperBound() {
    return P_EQUALS_MAX;
  }

  @Override // from AbstractDiscreteDistribution
  protected Scalar protected_p_equals(int n) {
    if (P_EQUALS_MAX < n)
      return RealScalar.ZERO;
    while (values.length() <= n) {
      Scalar factor = lambda.divide(RationalScalar.of(values.length(), 1));
      values.append(Last.of(values).multiply(factor));
    }
    return values.Get(n);
  }

  @Override // from Object
  public String toString() {
    return String.format("%s[%s]", getClass().getSimpleName(), lambda);
  }
}
