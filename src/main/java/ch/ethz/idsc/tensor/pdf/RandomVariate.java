// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Array;

/** RandomVariate generates single random variates, or arrays of random variates
 * from a given {@link Distribution}, or {@link RandomVariateInterface}.
 * 
 * Example:
 * <code>
 * Distribution distribution = NormalDistribution.standard();
 * Tensor matrix = RandomVariate.of(distribution, 5, 3);
 * </code>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/RandomVariate.html">RandomVariate</a> */
public enum RandomVariate {
  ;
  private static final Random RANDOM = new Random();

  /** @param randomVariateInterface
   * @param random
   * @return random variate from given interface */
  public static Scalar of(RandomVariateInterface randomVariateInterface, Random random) {
    return randomVariateInterface.randomVariate(random); // terminal
  }

  /** @param randomVariateInterface
   * @return random variate from given interface */
  public static Scalar of(RandomVariateInterface randomVariateInterface) {
    return of(randomVariateInterface, RANDOM); // of # interface, random
  }

  // ---
  /** @param randomVariateInterface
   * @param random
   * @param dimensions
   * @return array of random variates from given interface with given dimensions */
  public static Tensor of(RandomVariateInterface randomVariateInterface, Random random, List<Integer> dimensions) {
    return Array.of(list -> of(randomVariateInterface, random), dimensions); // terminal
  }

  /** @param randomVariateInterface
   * @param dimensions
   * @return array of random variates with given dimensions */
  public static Tensor of(RandomVariateInterface randomVariateInterface, List<Integer> dimensions) {
    return of(randomVariateInterface, RANDOM, dimensions); // of # interface, random, list
  }

  // ---
  /** @param randomVariateInterface
   * @param random
   * @param dimensions
   * @return array of random variates from given interface with given dimensions */
  public static Tensor of(RandomVariateInterface randomVariateInterface, Random random, Integer... dimensions) {
    return of(randomVariateInterface, random, Arrays.asList(dimensions)); // of # interface, random, list
  }

  /** @param randomVariateInterface
   * @param dimensions
   * @return array of random variates with given dimensions */
  public static Tensor of(RandomVariateInterface randomVariateInterface, Integer... dimensions) {
    return of(randomVariateInterface, Arrays.asList(dimensions)); // of # interface, list
  }
}
