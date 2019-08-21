// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.function.UnaryOperator;

import ch.ethz.idsc.tensor.Integers;
import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Nest.html">Nest</a> */
public enum Nest {
  ;
  /** @param unaryOperator
   * @param x
   * @param n non-negative
   * @return */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(UnaryOperator<T> unaryOperator, T x, int n) {
    if (0 == Integers.requirePositiveOrZero(n))
      return (T) x.copy(); // <- specific to Tensor interface
    for (int index = 0; index < n; ++index)
      x = unaryOperator.apply(x);
    return x;
  }
}
