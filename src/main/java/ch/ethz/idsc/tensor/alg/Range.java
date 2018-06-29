// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.stream.LongStream;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;

/** Range is consistent with {@link LongStream#range(long, long)}
 * 
 * <p>Careful: the implementation deviates from Mathematica:
 * Mathematica::Range[2, 7] == {2, 3, 4, 5, 6, 7}
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Range.html">Range</a> */
public enum Range {
  ;
  /** <pre>
   * Range.of(0, 4) == {0, 1, 2, 3}
   * Range.of(2, 7) == {2, 3, 4, 5, 6}
   * Range.of(6, 6) == {}
   * Range.of(9, 3) == {}
   * </pre>
   * 
   * @param startInclusive
   * @param endExclusive
   * @return vector of the form {startInclusive, startInclusive + 1, ..., endExclusive - 1},
   * or the empty vector if startInclusive is greater or equal to endExclusive
   * @see Subdivide */
  public static Tensor of(long startInclusive, long endExclusive) {
    return Tensor.of(LongStream.range(startInclusive, endExclusive).mapToObj(RealScalar::of));
  }
}
