// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Tensor;

/** simplified version of Mathematica::Differences
 * 
 * <p>The implementation is consistent for the special cases
 * <pre>
 * Differences[ {} ] == {}
 * Differences[ {single} ] == {}
 * </pre>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Differences.html">Differences</a> */
public enum Differences {
  ;
  /** <pre>
   * Differences[{a, b, c, d, e}] == {b - a, c - b, d - c, e - d}
   * </pre>
   * 
   * @param tensor
   * @return the successive differences of elements in tensor */
  public static Tensor of(Tensor tensor) {
    return Tensor.of(IntStream.range(0, tensor.length() - 1) //
        .mapToObj(index -> tensor.get(index + 1).subtract(tensor.get(index))));
  }
}
