// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.sca.Clip;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/TruncatedDistribution.html">TruncatedDistribution</a> */
public class TruncatedDistribution implements Distribution, RandomVariateInterface, Serializable {
  /** maximum number of attempts to produce a random variate before an exception is thrown */
  private static final int MAX_ITERATIONS = 100;

  /** Careful: function does not check for plausibility of input
   * 
   * @param distribution non-null
   * @param clip non-null
   * @return
   * @throws Exception if either parameter is null */
  public static Distribution of(Distribution distribution, Clip clip) {
    return new TruncatedDistribution( //
        Objects.requireNonNull((RandomVariateInterface) distribution), //
        Objects.requireNonNull(clip));
  }

  // ---
  private final RandomVariateInterface randomVariateInterface;
  private final Clip clip;

  private TruncatedDistribution(RandomVariateInterface randomVariateInterface, Clip clip) {
    this.randomVariateInterface = randomVariateInterface;
    this.clip = clip;
  }

  @Override // from RandomVariateInterface
  public Scalar randomVariate(Random random) {
    return Stream.generate(() -> randomVariateInterface.randomVariate(random)) //
        .limit(MAX_ITERATIONS) //
        .filter(clip::isInside) //
        .findFirst().get();
  }
}
