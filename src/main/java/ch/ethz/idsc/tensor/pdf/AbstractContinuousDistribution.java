// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.io.Serializable;
import java.util.Random;

import ch.ethz.idsc.tensor.Scalar;

public abstract class AbstractContinuousDistribution implements ContinuousDistribution, Serializable {
  @Override // from RandomVariateInterface
  public final Scalar randomVariate(Random random) {
    return randomVariate(random.nextDouble());
  }

  @Override // from CDF
  public final Scalar p_lessEquals(Scalar x) {
    return p_lessThan(x);
  }

  /***************************************************/
  /** @param reference uniformly distributed in the interval [0, 1)
   * @return */
  protected abstract Scalar randomVariate(double reference);
}
