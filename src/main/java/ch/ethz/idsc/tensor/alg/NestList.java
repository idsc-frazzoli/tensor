// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.function.UnaryOperator;

import ch.ethz.idsc.tensor.Integers;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/NestList.html">NestList</a> */
public enum NestList {
  ;
  /** gives a list of the results of applying f to x 0 through n times
   * 
   * <pre>
   * NestList[f, x, 4] == {x, f[x], f[f[x]], f[f[f[x]]], f[f[f[f[x]]]]}
   * </pre>
   * 
   * The special case f==null and n==0 does not throw an exception:
   * NestList.of(null, x, 0) == {x}
   * 
   * @param unaryOperator function "f"
   * @param x
   * @param n non-negative
   * @return tensor of length n + 1
   * @throws Exception if n is negative */
  public static <T extends Tensor> Tensor of(UnaryOperator<T> unaryOperator, T x, int n) {
    Integers.requirePositiveOrZero(n);
    Tensor tensor = Tensors.reserve(n + 1).append(x);
    for (int index = 0; index < n; ++index)
      tensor.append(x = unaryOperator.apply(x));
    return tensor;
  }
}
