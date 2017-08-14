// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Subdivide.html">Subdivide</a> */
public enum Subdivide {
  ;
  /** Subdivide.of(RealScalar.of(10), RealScalar.of(15), 5)
   * results in
   * [10, 11, 12, 13, 14, 15]
   * 
   * Subdivide also works for descending values:
   * Subdivide.of(RealScalar.of(-1), RealScalar.of(-4), 3)
   * results in
   * [-1, -2, -3, -4]
   * 
   * Subdivide also works for vectors:
   * Subdivide.of(Tensors.vector(10, 5), Tensors.vector(5, 15), 4)
   * 
   * @param startInclusive
   * @param endInclusive
   * @param n > 0
   * @return tensor with n+1 entries obtained by subdividing the range
   * startInclusive to endInclusive into n equal parts. */
  public static Tensor of(Tensor startInclusive, Tensor endInclusive, int n) {
    return Tensor.of(IntStream.rangeClosed(0, n) //
        .mapToObj(count -> startInclusive.multiply(RationalScalar.of(n - count, n)) //
            .add(endInclusive.multiply(RationalScalar.of(count, n)))));
  }

  /** see description above
   * 
   * @param startInclusive
   * @param endInclusive
   * @param n
   * @return */
  public static Tensor of(Number startInclusive, Number endInclusive, int n) {
    return of(RealScalar.of(startInclusive), RealScalar.of(endInclusive), n);
  }
}
