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
 * <pre>
 * Distribution distribution = NormalDistribution.standard();
 * Tensor matrix = RandomVariate.of(distribution, 5, 3);
 * </pre>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/RandomVariate.html">RandomVariate</a> */
public enum RandomVariate {
  ;
  /** The default constructor of {@link Random} determines the seed at time of creation
   * using {@link System#nanoTime()}. Typically, the implementation will produce a different
   * random sequence for two successive program executions. */
  private static final Random RANDOM = new Random();

  /** @param randomVariateInterface
   * @param random
   * @return random variate from given interface */
  public static Scalar of(Distribution distribution, Random random) {
    return _of((RandomVariateInterface) distribution, random); // terminal
  }

  /** @param randomVariateInterface
   * @return random variate from given interface */
  public static Scalar of(Distribution distribution) {
    return of(distribution, RANDOM); // of # interface, random
  }

  // ---
  /** @param randomVariateInterface
   * @param random
   * @param dimensions
   * @return array of random variates from given interface with given dimensions */
  public static Tensor of(Distribution distribution, Random random, List<Integer> dimensions) {
    RandomVariateInterface randomVariateInterface = (RandomVariateInterface) distribution;
    return Array.of(list -> _of(randomVariateInterface, random), dimensions); // terminal
  }

  /** @param randomVariateInterface
   * @param dimensions
   * @return array of random variates with given dimensions */
  public static Tensor of(Distribution distribution, List<Integer> dimensions) {
    return of(distribution, RANDOM, dimensions); // of # interface, random, list
  }

  // ---
  /** @param randomVariateInterface
   * @param random
   * @param dimensions
   * @return array of random variates from given interface with given dimensions */
  public static Tensor of(Distribution distribution, Random random, Integer... dimensions) {
    return of(distribution, random, Arrays.asList(dimensions)); // of # interface, random, list
  }

  /** @param randomVariateInterface
   * @param dimensions
   * @return array of random variates with given dimensions */
  public static Tensor of(Distribution distribution, Integer... dimensions) {
    return of(distribution, Arrays.asList(dimensions)); // of # interface, list
  }

  // helper function
  private static Scalar _of(RandomVariateInterface randomVariateInterface, Random random) {
    return randomVariateInterface.randomVariate(random);
  }
}
