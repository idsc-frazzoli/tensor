// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;

/** Range deviates marginally from Mathematica's Range.
 * 
 * inspired by
 * <a href="https://reference.wolfram.com/language/ref/Range.html">Range</a> */
public enum Range {
  ;
  /** Range.of(5) gives
   * [0, 1, 2, 3, 4]
   * 
   * @param length
   * @return */
  public static Tensor of(int length) {
    return of(0, length);
  }

  /** Range.of(2, 7) gives
   * [2, 3, 4, 5, 6]
   * 
   * @param startInclusive
   * @param endExclusive
   * @return */
  public static Tensor of(int startInclusive, int endExclusive) {
    return Tensor.of(IntStream.range(startInclusive, endExclusive).boxed().map(RealScalar::of));
  }
}
