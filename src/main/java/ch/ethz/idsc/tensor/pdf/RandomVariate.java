// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.Scalar;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/RandomVariate.html">RandomVariate</a> */
public enum RandomVariate {
  ;
  // ---
  private static final Random RANDOM = new Random();

  /** @param randomVariateInterface
   * @return */
  public static Scalar of(RandomVariateInterface randomVariateInterface) {
    return of(randomVariateInterface, RANDOM);
  }

  /** @param randomVariateInterface
   * @param random
   * @return random variate from given interface */
  public static Scalar of(RandomVariateInterface randomVariateInterface, Random random) {
    return randomVariateInterface.randomVariate(random);
  }
}
