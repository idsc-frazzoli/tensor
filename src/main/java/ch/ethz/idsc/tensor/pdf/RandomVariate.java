// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Array;

/** Example:
 * <code>
 * Distribution distribution = NormalDistribution.of();
 * Tensor matrix = RandomVariate.of(distribution, 5, 3);
 * </code>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/RandomVariate.html">RandomVariate</a> */
public enum RandomVariate {
  ;
  // ---
  private static final Random RANDOM = new Random();

  /** @param randomVariateInterface
   * @return random variate from given interface */
  public static Scalar of(RandomVariateInterface randomVariateInterface) {
    return of(randomVariateInterface, RANDOM);
  }

  /** @param randomVariateInterface
   * @param random
   * @return random variate from given interface */
  public static Scalar of(RandomVariateInterface randomVariateInterface, Random random) {
    return randomVariateInterface.randomVariate(random);
  }

  /** @param randomVariateInterface
   * @param dimensions
   * @return array of random variates with given dimensions */
  public static Tensor of(RandomVariateInterface randomVariateInterface, Integer... dimensions) {
    return of(randomVariateInterface, RANDOM, dimensions);
  }

  /** @param randomVariateInterface
   * @param random
   * @param dimensions
   * @return array of random variates from given interface with given dimensions */
  public static Tensor of(RandomVariateInterface randomVariateInterface, Random random, Integer... dimensions) {
    return Array.of(list -> of(randomVariateInterface, random), dimensions);
  }
}
