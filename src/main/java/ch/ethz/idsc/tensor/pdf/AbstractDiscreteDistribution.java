// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;

/** functionality for a discrete probability distribution */
public abstract class AbstractDiscreteDistribution implements DiscreteDistribution, RandomVariateInterface {
  @Override
  public Scalar randomVariate(Random random) {
    Scalar reference = RealScalar.of(random.nextDouble());
    int sample = lowerBound();
    Scalar cumprob = p_equals(sample);
    while (Scalars.lessThan(cumprob, reference))
      cumprob = cumprob.add(p_equals(++sample));
    return RealScalar.of(sample);
  }
}
