// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.Clip;
import ch.ethz.idsc.tensor.sca.Exp;
import ch.ethz.idsc.tensor.sca.Log;
import ch.ethz.idsc.tensor.sca.Sign;

/** Characteristics of an exponential distribution:
 * <ul>
 * <li>Random variates are non-negative real numbers.
 * <li>The distribution depends on a rate-of-decay parameter lambda.
 * Increasing lambda results in a distribution with mean moving towards 0,
 * i.e. faster decay.
 * </ul>
 * 
 * <p>The InverseCDF at p == 1 evaluates to DoubleScalar.POSITIVE_INFINITY.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ExponentialDistribution.html">ExponentialDistribution</a> */
public class ExponentialDistribution extends AbstractContinuousDistribution implements //
    InverseCDF, MeanInterface, VarianceInterface {
  /** @param lambda positive, may be instance of {@link Quantity}
   * @return exponential distribution with scale inversely proportional to parameter lambda */
  public static Distribution of(Scalar lambda) {
    return new ExponentialDistribution(Sign.requirePositive(lambda));
  }

  /** @param lambda positive
   * @return exponential distribution with scale inversely proportional to parameter lambda */
  public static Distribution of(Number lambda) {
    return of(RealScalar.of(lambda));
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
    return quantile_unit(DoubleScalar.of(Math.nextUp(reference)));
  }

  @Override // from InverseCDF
  public Scalar quantile(Scalar p) {
    return quantile_unit(RealScalar.ONE.subtract(Clip.unit().requireInside(p)));
  }

  private Scalar quantile_unit(Scalar p) {
    return Log.FUNCTION.apply(p).divide(lambda_negate);
  }

  @Override // from MeanInterface
  public Scalar mean() {
    return lambda.reciprocal();
  }

  @Override // from VarianceInterface
  public Scalar variance() {
    return lambda.multiply(lambda).reciprocal();
  }

  @Override // from PDF
  public Scalar at(Scalar x) {
    if (Sign.isNegative(x))
      return lambda.zero();
    return Exp.FUNCTION.apply(x.multiply(lambda_negate)).multiply(lambda);
  }

  @Override // from CDF
  public Scalar p_lessThan(Scalar x) {
    return Sign.isNegativeOrZero(x) ? RealScalar.ZERO : //
        RealScalar.ONE.subtract(Exp.FUNCTION.apply(x.multiply(lambda_negate)));
  }

  @Override // from CDF
  public Scalar p_lessEquals(Scalar x) {
    return p_lessThan(x);
  }

  @Override // from Object
  public String toString() {
    return String.format("%s[%s]", getClass().getSimpleName(), lambda);
  }
}
