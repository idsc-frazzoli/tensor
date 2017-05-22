// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.stream.LongStream;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;

/** Range.of(2, 7) == {2, 3, 4, 5, 6}
 * 
 * Range is consistent with {@link LongStream#range(long, long)}
 * 
 * Careful: the tensor API deviates from Mathematica's Range:
 * Mathematica::Range[2, 7] == {2, 3, 4, 5, 6, 7}
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Range.html">Range</a> */
public enum Range {
  ;
  /** Range.of(2, 7) gives
   * {2, 3, 4, 5, 6}
   * 
   * @param startInclusive
   * @param endExclusive
   * @return */
  public static Tensor of(long startInclusive, long endExclusive) {
    return Tensor.of(LongStream.range(startInclusive, endExclusive).boxed().map(RealScalar::of));
  }
}
