// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.function.BiFunction;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Outer.html">Outer</a> */
public enum Outer {
  ;
  /** @param binaryOperator
   * @param a
   * @param b
   * @return */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor, U extends Tensor> //
  Tensor of(BiFunction<T, U, ? extends Tensor> binaryOperator, Tensor a, Tensor b) {
    return Tensors.matrix((i, j) -> binaryOperator.apply((T) a.get(i), (U) b.get(j)), a.length(), b.length());
  }
}
