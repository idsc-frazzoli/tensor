// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;

public enum Range {
  ;
  /** @param length
   * @return */
  public static Tensor of(int length) {
    return Tensor.of(IntStream.range(0, length).boxed().map(i -> RealScalar.of(i)));
  }
  // TODO test

  /** @param startInclusive
   * @param endExclusive
   * @return */
  public static Tensor of(int startInclusive, int endExclusive) {
    return Tensor.of(IntStream.range(startInclusive, endExclusive) //
        .boxed().map(i -> RealScalar.of(i)));
  }
}
