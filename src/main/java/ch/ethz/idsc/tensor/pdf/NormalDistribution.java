// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/NormalDistribution.html">NormalDistribution</a> */
public class NormalDistribution implements ContinuousDistribution {
  public static ContinuousDistribution of() {
    return new NormalDistribution();
  }

  private NormalDistribution() {
  }

  @Override
  public Scalar p_lessThan(Scalar x) {
    return null;
  }

  @Override
  public Scalar p_lessEquals(Scalar x) {
    return p_lessThan(x);
  }

  @Override
  public Scalar nextSample(Random random) {
    return RealScalar.of(random.nextGaussian());
  }
}
