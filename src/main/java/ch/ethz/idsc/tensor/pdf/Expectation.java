// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Objects;
import java.util.function.Function;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.AbsSquared;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** Careful:
 * {@link Expectation} does not work well for distributions that have
 * infinite support and at the same time exact probabilities,
 * for example: {@link GeometricDistribution}
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Expectation.html">Expectation</a> */
public enum Expectation {
  ;
  /** @param scalarUnaryOperator
   * @param distribution
   * @return */
  public static <T extends Tensor> T of(Function<Scalar, T> function, Distribution distribution) {
    if (distribution instanceof DiscreteDistribution)
      return _of(function, (DiscreteDistribution) distribution);
    throw new RuntimeException();
  }

  /** @param distribution
   * @return mean of distribution, E[X] */
  public static Scalar mean(Distribution distribution) {
    if (distribution instanceof MeanInterface) {
      MeanInterface meanInterface = (MeanInterface) distribution;
      return meanInterface.mean();
    }
    return of(Function.identity(), distribution);
  }

  /** @param distribution
   * @return variance of distribution, E[ |X-E[X]|^2 ] */
  public static Scalar variance(Distribution distribution) {
    if (distribution instanceof VarianceInterface) {
      VarianceInterface varianceInterface = (VarianceInterface) distribution;
      return varianceInterface.variance();
    }
    Scalar mean = mean(distribution);
    ScalarUnaryOperator scalarUnaryOperator = scalar -> AbsSquared.FUNCTION.apply(scalar.subtract(mean));
    return of(scalarUnaryOperator, distribution);
  }

  @SuppressWarnings("unchecked")
  private static <T extends Tensor> T _of(Function<Scalar, T> function, DiscreteDistribution discreteDistribution) {
    T value = null;
    Scalar p_equals = RealScalar.ZERO;
    Scalar cumprob = RealScalar.ZERO;
    int sample = discreteDistribution.lowerBound();
    while (!DiscreteCDF.isFinished(p_equals, cumprob)) {
      Scalar x = RationalScalar.of(sample, 1);
      p_equals = discreteDistribution.p_equals(sample);
      cumprob = cumprob.add(p_equals);
      T delta = (T) function.apply(x).multiply(p_equals);
      value = Objects.isNull(value) ? delta : (T) value.add(delta);
      ++sample;
    }
    return value;
  }
}
