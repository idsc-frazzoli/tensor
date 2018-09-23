// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.qty.Quantity;

/** Subdivide is consistent with Mathematica.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Subdivide.html">Subdivide</a> */
public enum Subdivide {
  ;
  /** Examples:
   * <pre>
   * Subdivide.of(10, 15, 5) == {10, 11, 12, 13, 14, 15}
   * Subdivide.of(10, 15, 4) == {10, 45/4, 25/2, 55/4, 15}
   * </pre>
   * 
   * Subdivide operates on decreasing values:
   * <pre>
   * Subdivide.of(RealScalar.of(-1), RealScalar.of(-4), 3) == {-1, -2, -3, -4}
   * </pre>
   * 
   * Subdivide operates on vectors:
   * <pre>
   * Subdivide.of(Tensors.vector(10, 5), Tensors.vector(5, 15), 4)
   * </pre>
   * 
   * Subdivide operates on {@link Quantity}:
   * <pre>
   * Subdivide.of(Quantity.of(-20, "deg"), Quantity.of(20, "deg"), 4)
   * == {-20[deg], -10[deg], 0[deg], 10[deg], 20[deg]}
   * </pre>
   * 
   * @param startInclusive
   * @param endInclusive
   * @param n > 0
   * @return tensor with n+1 entries obtained by subdividing the range
   * startInclusive to endInclusive into n equal parts.
   * @throws Exception if n is negative or zero
   * @see Range */
  public static Tensor of(Tensor startInclusive, Tensor endInclusive, int n) {
    if (0 < n)
      return Tensor.of(IntStream.rangeClosed(0, n) //
          .mapToObj(count -> startInclusive.multiply(RationalScalar.of(n - count, n)) //
              .add(endInclusive.multiply(RationalScalar.of(count, n)))));
    throw new RuntimeException("n=" + n);
  }

  /** see description above
   * 
   * @param startInclusive
   * @param endInclusive
   * @param n > 0
   * @return tensor with n+1 entries obtained by subdividing the range */
  public static Tensor of(Number startInclusive, Number endInclusive, int n) {
    return of(RealScalar.of(startInclusive), RealScalar.of(endInclusive), n);
  }
}
