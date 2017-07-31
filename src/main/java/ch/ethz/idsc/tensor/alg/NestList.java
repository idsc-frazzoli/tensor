// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.function.UnaryOperator;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/NestList.html">NestList</a> */
public enum NestList {
  ;
  /** gives a list of the results of applying f to x 0 through n times
   * 
   * @param unaryOperator
   * @param x
   * @param n non-negative
   * @return tensor of length n + 1 */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> Tensor of(UnaryOperator<T> unaryOperator, T x, int n) {
    Tensor tensor = Tensors.of(x);
    for (int index = 0; index < n; ++index)
      tensor.append(unaryOperator.apply((T) Last.of(tensor)));
    return tensor;
  }
}
