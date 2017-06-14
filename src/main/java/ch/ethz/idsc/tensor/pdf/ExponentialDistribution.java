// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.sca.Exp;
import ch.ethz.idsc.tensor.sca.Log;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ExponentialDistribution.html">ExponentialDistribution</a> */
public class ExponentialDistribution implements ContinuousDistribution {
  /** @param lambda positive
   * @return */
  public static Distribution of(Scalar lambda) {
    if (Scalars.lessEquals(lambda, RealScalar.ZERO))
      throw TensorRuntimeException.of(lambda);
    return new ExponentialDistribution(lambda);
  }

  // ---
  private final Scalar lambda;
  private final Scalar lambda_negate;

  private ExponentialDistribution(Scalar lambda) {
    this.lambda = lambda;
    lambda_negate = lambda.negate();
  }

  @Override // from RandomVariateInterface
  public Scalar randomVariate(Random random) {
    // {@link Random#nextDouble()} samples uniformly from the range 0.0 (inclusive) to 1.0d (exclusive)
    double uniform = Math.nextUp(random.nextDouble());
    return Log.of(RealScalar.of(uniform)).divide(lambda_negate);
  }

  @Override // from Distribution
  public Scalar mean() {
    return lambda.invert();
  }

  @Override // from Distribution
  public Scalar variance() {
    return lambda.multiply(lambda).invert();
  }

  @Override // from ContinuousDistribution
  public Scalar p_lessThan(Scalar x) {
    return RealScalar.ONE.subtract(Exp.of(x.multiply(lambda_negate)));
  }

  @Override // from ContinuousDistribution
  public Scalar p_lessEquals(Scalar x) {
    return p_lessThan(x);
  }
}
