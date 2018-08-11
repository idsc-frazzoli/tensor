// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.io.Serializable;
import java.util.Random;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.sca.Clip;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/TruncatedDistribution.html">TruncatedDistribution</a> */
public class TruncatedDistribution implements Distribution, RandomVariateInterface, Serializable {
  /** Careful: function does not check for plausibility of input
   * 
   * @param distribution
   * @param clip
   * @return */
  public static Distribution of(Distribution distribution, Clip clip) {
    return new TruncatedDistribution(distribution, clip);
  }

  // ---
  private final Distribution distribution;
  private final Clip clip;

  private TruncatedDistribution(Distribution distribution, Clip clip) {
    this.distribution = distribution;
    this.clip = clip;
  }

  @Override // from RandomVariateInterface
  public Scalar randomVariate(Random random) {
    while (true) {
      Scalar scalar = RandomVariate.of(distribution);
      if (clip.isInside(scalar))
        return scalar;
    }
  }
}
