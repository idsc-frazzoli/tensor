// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.function.Function;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** Careful:
 * does not work well for distributions with infinite support and exact probabilities,
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

  @SuppressWarnings("unchecked")
  private static <T extends Tensor> T _of(Function<Scalar, T> function, DiscreteDistribution discreteDistribution) {
    T value = null;
    Scalar p_equals = RealScalar.ZERO;
    Scalar cumprob = RealScalar.ZERO;
    int sample = discreteDistribution.lowerBound();
    while (!DiscreteCDF.isFinished(p_equals, cumprob)) {
      Scalar x = RealScalar.of(sample);
      p_equals = discreteDistribution.p_equals(sample);
      cumprob = cumprob.add(p_equals);
      T delta = (T) function.apply(x).multiply(p_equals);
      value = value == null ? delta : (T) value.add(delta);
      ++sample;
    }
    return value;
  }
}
