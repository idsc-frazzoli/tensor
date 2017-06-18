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
public class ExponentialDistribution implements Distribution, //
    CDF, MeanInterface, PDF, RandomVariateInterface, VarianceInterface {
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
    return randomVariate(random.nextDouble());
  }

  /* package for testing */ Scalar randomVariate(double reference) {
    // {@link Random#nextDouble()} samples uniformly from the range 0.0 (inclusive) to 1.0d (exclusive)
    double uniform = Math.nextUp(reference);
    return Log.of(RealScalar.of(uniform)).divide(lambda_negate);
  }

  @Override // from MeanInterface
  public Scalar mean() {
    return lambda.invert();
  }

  @Override // from VarianceInterface
  public Scalar variance() {
    return lambda.multiply(lambda).invert();
  }

  @Override // from PDF
  public Scalar at(Scalar x) {
    if (Scalars.lessThan(x, RealScalar.ZERO))
      return RealScalar.ZERO;
    return Exp.of(x.multiply(lambda).negate()).multiply(lambda); // E^(-x \[Lambda]) \[Lambda]
  }

  @Override // from CDF
  public Scalar p_lessThan(Scalar x) {
    return Scalars.lessEquals(x, RealScalar.ZERO) ? RealScalar.ZERO : //
        RealScalar.ONE.subtract(Exp.of(x.multiply(lambda_negate)));
  }

  @Override // from CDF
  public Scalar p_lessEquals(Scalar x) {
    return p_lessThan(x);
  }
}
