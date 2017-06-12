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

  /** @param distribution
   * @return */
  public static Scalar of(Distribution distribution) {
    return of(distribution, RANDOM);
  }

  /** @param distribution
   * @param random
   * @return random variates from given probability density function */
  public static Scalar of(Distribution distribution, Random random) {
    if (distribution instanceof RandomVariateInterface) {
      RandomVariateInterface randomVariateInterface = (RandomVariateInterface) distribution;
      return randomVariateInterface.randomVariate(random);
    }
    throw new RuntimeException();
  }
}
